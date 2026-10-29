import { render, screen, waitFor } from "@testing-library/react";
import { useGetProducts } from "./useProducts";
import axios from "axios";
import React from "react";

// Mock de axios para simular llamadas HTTP
jest.mock("axios");

// Componente de prueba que consume el hook useGetProducts
// y muestra el estado (loading, data, error) en el DOM
function TestComponent({ term }) {
  const { data, loading, error } = useGetProducts(term);
  return (
    <div>
      <span data-testid="loading">{loading ? "loading" : "not-loading"}</span>
      <span data-testid="data">{JSON.stringify(data)}</span>
      <span data-testid="error">{error}</span>
    </div>
  );
}

describe("useGetProducts (useProducts.js)", () => {
  // Limpia todos los mocks después de cada test para evitar interferencias
  afterEach(() => {
    jest.clearAllMocks();
  });

  // Test: Verifica que el hook busca y carga productos correctamente desde la API
  it("debe cargar datos correctamente", async () => {
    const mockData = [{ id: 1, name: "Producto1" }];
    axios.get.mockResolvedValueOnce({ data: mockData });

    render(<TestComponent term="Pan" />);
    // Verifica que inicialmente está cargando
    expect(screen.getByTestId("loading").textContent).toBe("loading");
    // Espera a que el loading termine y verifica que los datos se muestren
    await waitFor(() =>
      expect(screen.getByTestId("loading").textContent).toBe("not-loading")
    );
    expect(screen.getByTestId("data").textContent).toContain("Producto1");
    expect(screen.getByTestId("error").textContent).toBe("");
  });

  // Test: Verifica que el hook maneja errores de búsqueda de productos correctamente
  it("debe manejar errores", async () => {
    axios.get.mockRejectedValueOnce(new Error("Error!"));

    render(<TestComponent term="Pan" />);
    // Verifica que inicialmente está cargando
    expect(screen.getByTestId("loading").textContent).toBe("loading");
    // Espera a que el loading termine y verifica que no haya datos ni error
    await waitFor(() =>
      expect(screen.getByTestId("loading").textContent).toBe("not-loading")
    );
    expect(screen.getByTestId("data").textContent).toBe("[]");
    expect(screen.getByTestId("error").textContent).toBe("");
  });
});
