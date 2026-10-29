import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import RecetaComentario from "./RecetaComentario";
import { AuthProvider } from "../../context/AuthContext";

beforeAll(() => {
  process.env.REACT_APP_API_URL = "http://localhost:4000";
});

// Mock de axios para simular la petición POST
jest.mock("axios");
import axios from "axios";

// Mock de contexto de usuario logueado
const mockUser = {
  uid: "user123",
  displayName: "Test User",
  photoURL: "/img/avatars/default.png",
  getIdToken: jest.fn().mockResolvedValue("token123"),
};

// Mock de useAuth para devolver el usuario simulado
jest.mock("../../context/AuthContext", () => ({
  useAuth: () => ({ user: mockUser }),
}));

describe("RecetaComentario", () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  // Test: Renderizado básico del formulario de comentario
  it("renderiza el formulario de comentario", () => {
    render(<RecetaComentario recipeId="receta1" onCommentAdded={jest.fn()} />);
    expect(
      screen.getByPlaceholderText(/escribe tu comentario/i)
    ).toBeInTheDocument();
    expect(screen.getByText(/publicar/i)).toBeInTheDocument();
  });

  // Test: Validación de campos vacíos (no deja publicar si falta texto o estrellas)
  it("muestra error si no se completa el comentario o la valoración", async () => {
    render(<RecetaComentario recipeId="receta1" onCommentAdded={jest.fn()} />);
    fireEvent.click(screen.getByText(/publicar/i));
    expect(
      await screen.findByText(/completa el comentario y la valoración/i)
    ).toBeInTheDocument();
  });

  // Test: Publicación exitosa de un comentario
  it("publica el comentario correctamente", async () => {
    axios.post.mockResolvedValueOnce({});
    const onCommentAdded = jest.fn();
    render(
      <RecetaComentario recipeId="receta1" onCommentAdded={onCommentAdded} />
    );
    // Escribe comentario
    fireEvent.change(screen.getByPlaceholderText(/escribe tu comentario/i), {
      target: { value: "¡Excelente receta!" },
    });
    // Selecciona 4 estrellas
    fireEvent.click(screen.getByTestId("star-4"));
    // Publica
    fireEvent.click(screen.getByText(/publicar/i));
    // Espera mensaje de éxito y verifica que se llamó el callback y axios
    expect(
      await screen.findByText(/comentario publicado/i)
    ).toBeInTheDocument();
    expect(onCommentAdded).toHaveBeenCalled();
    expect(axios.post).toHaveBeenCalledWith(
      expect.stringContaining("/review"),
      expect.objectContaining({
        recipeId: "receta1",
        rating: 4,
        uid: mockUser.uid,
        content: "¡Excelente receta!",
      }),
      expect.any(Object)
    );
  });

  // Test: Manejo de error al publicar (simula fallo de axios)
  it("muestra error si axios falla", async () => {
    axios.post.mockRejectedValueOnce(new Error("Error!"));
    render(<RecetaComentario recipeId="receta1" onCommentAdded={jest.fn()} />);
    fireEvent.change(screen.getByPlaceholderText(/escribe tu comentario/i), {
      target: { value: "Comentario" },
    });
    fireEvent.click(screen.getByTestId("star-5"));
    fireEvent.click(screen.getByText(/publicar/i));
    expect(await screen.findByText(/no se pudo publicar/i)).toBeInTheDocument();
  });
});
