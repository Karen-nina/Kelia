import { render, screen, waitFor } from "@testing-library/react";
import { useGetProductsByType } from "./productByType";
import axios from "axios";
import React from "react";

// Mock de axios para simular llamadas HTTP
jest.mock("axios");

// Componente de prueba que consume el hook useGetProductsByType
// y muestra el estado (loading, data, error) en el DOM
function TestComponent({ type }) {
  const { data, loading, error } = useGetProductsByType(type);
  return (
    <div>
      <span data-testid="loading">{loading ? "loading" : "not-loading"}</span>
      <span data-testid="data">{JSON.stringify(data)}</span>
      <span data-testid="error">{error}</span>
    </div>
  );
}

describe("useGetProductsByType", () => {
  // Limpia todos los mocks después de cada test para evitar interferencias
  afterEach(() => {
    jest.clearAllMocks();
  });

  // Test: Verifica que el hook carga productos por tipo correctamente desde la API
  it("debe cargar datos correctamente", async () => {
    const mockData = [{ id: 1, name: "Producto1" }];
    axios.get.mockResolvedValueOnce({ data: mockData });

    render(<TestComponent type="Tipo1" />);
    // Espera a que el loading termine y verifica que los datos se muestren
    await waitFor(() =>
      expect(screen.getByTestId("loading").textContent).toBe("not-loading")
    );
    expect(screen.getByTestId("data").textContent).toContain("Producto1");
    expect(screen.getByTestId("error").textContent).toBe("");
  });

  // Test: Verifica que el hook maneja errores de carga de productos por tipo correctamente
  it("debe manejar errores", async () => {
    axios.get.mockRejectedValueOnce(new Error("Error!"));

    render(<TestComponent type="Tipo1" />);
    // Espera a que el loading termine y verifica que se muestre el error
    await waitFor(() =>
      expect(screen.getByTestId("loading").textContent).toBe("not-loading")
    );
    expect(screen.getByTestId("data").textContent).toBe("[]");
    expect(screen.getByTestId("error").textContent).toBe("Error!");
  });
});
