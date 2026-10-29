import { render, screen, waitFor } from "@testing-library/react";
import { useGetProducts } from "./products";
import axios from "axios";
import React from "react";

jest.mock("axios");

// Componente de prueba que consume el hook useGetProducts
// y muestra el estado (loading, data, error) en el DOM
function TestComponent({ searchTerms }) {
  const { data, loading, error } = useGetProducts(searchTerms);
  return (
    <div>
      <span data-testid="loading">{loading ? "loading" : "not-loading"}</span>
      <span data-testid="data">{JSON.stringify(data)}</span>
      <span data-testid="error">{error}</span>
    </div>
  );
}

describe("useGetProducts (products.js)", () => {
  // Limpia todos los mocks después de cada test para evitar interferencias
  afterEach(() => {
    jest.clearAllMocks();
  });

  // Test: Verifica que el hook busca y carga productos correctamente desde la API
  it("debe cargar datos correctamente", async () => {
    const mockData = {
      "filter-name": ["Pan"],
      "filter-brand": ["Marca1"],
      "filter-type": ["Tipo1"],
      products: [
        { id: 1, salesName: "Pan", brand: "Marca1", productType: "Tipo1" },
      ],
    };
    axios.get.mockResolvedValueOnce({ data: { products: mockData.products } });

    render(<TestComponent searchTerms="Pan" />);
    // Verifica que inicialmente está cargando
    expect(screen.getByTestId("loading").textContent).toBe("loading");
    // Espera a que el loading termine y verifica que los datos se muestren
    await waitFor(() =>
      expect(screen.getByTestId("loading").textContent).toBe("not-loading")
    );
    expect(screen.getByTestId("data").textContent).toContain("Pan");
    expect(screen.getByTestId("error").textContent).toBe("");
  });

  // Test: Verifica que el hook maneja errores de búsqueda de productos correctamente
  it("debe manejar errores", async () => {
    axios.get.mockRejectedValueOnce(new Error("Error!"));

    render(<TestComponent searchTerms="Pan" />);
    // Verifica que inicialmente está cargando
    expect(screen.getByTestId("loading").textContent).toBe("loading");
    // Espera a que el loading termine y verifica que se muestre el error
    await waitFor(() =>
      expect(screen.getByTestId("loading").textContent).toBe("not-loading")
    );
    expect(screen.getByTestId("data").textContent).toContain("products"); // Vacío
    expect(screen.getByTestId("error").textContent).toBe("Error!");
  });
});
