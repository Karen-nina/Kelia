import { render, screen, waitFor } from "@testing-library/react";
import { useGetTypes } from "./type";
import axios from "axios";
import React from "react";

jest.mock("axios");

// Componente de prueba que consume el hook useGetTypes
// y muestra el estado (loading, data, error) en el DOM
function TestComponent({ page }) {
  const { data, loading, error } = useGetTypes(page);
  return (
    <div>
      <span data-testid="loading">{loading ? "loading" : "not-loading"}</span>
      <span data-testid="data">{JSON.stringify(data)}</span>
      <span data-testid="error">{error}</span>
    </div>
  );
}

describe("useGetTypes", () => {
  // Limpia todos los mocks después de cada test para evitar interferencias
  afterEach(() => {
    jest.clearAllMocks();
  });

  // Test: Verifica que el hook carga datos de tipos de producto correctamente desde la API
  it("debe cargar datos correctamente", async () => {
    const mockData = [{ id: 1, name: "Tipo1" }];
    axios.get.mockResolvedValueOnce({ data: mockData });

    render(<TestComponent page={1} />);
    // Espera a que el loading termine y verifica que los datos se muestren
    await waitFor(() =>
      expect(screen.getByTestId("loading").textContent).toBe("not-loading")
    );
    expect(screen.getByTestId("data").textContent).toContain("Tipo1");
    expect(screen.getByTestId("error").textContent).toBe("");
  });

  // Test: Verifica que el hook maneja errores de la API correctamente
  it("debe manejar errores", async () => {
    axios.get.mockRejectedValueOnce(new Error("Error!"));

    render(<TestComponent page={1} />);
    // Espera a que el loading termine y verifica que no haya datos ni error
    await waitFor(() =>
      expect(screen.getByTestId("loading").textContent).toBe("not-loading")
    );
    expect(screen.getByTestId("data").textContent).toBe("[]");
    expect(screen.getByTestId("error").textContent).toBe("");
  });
});
