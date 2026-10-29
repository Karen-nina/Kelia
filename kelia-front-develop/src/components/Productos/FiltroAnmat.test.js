import React from "react";
import { render, screen } from "@testing-library/react";
import FiltroAnmat from "./FiltroAnmat";

jest.mock("./FiltroMarca", () => ({ brands, onChange }) => {
  return (
    <div data-testid="filtro-marca">
      Brands: {brands.join(", ")}
      <button onClick={() => onChange(["brand1"])}>Change Brands</button>
    </div>
  );
});

jest.mock("./FiltroTipo", () => ({ types, onChange }) => {
  return (
    <div data-testid="filtro-tipo">
      Types: {types.join(", ")}
      <button onClick={() => onChange(["type1"])}>Change Types</button>
    </div>
  );
});

describe("FiltroAnmat", () => {
  test("RendersTitleAndChildComponentsWithCorrectProps", () => {
    const mockOnChangeBrands = jest.fn();
    const mockOnChangeTypes = jest.fn();

    const brands = ["BrandA", "BrandB"];
    const types = ["TypeX", "TypeY"];

    render(
      <FiltroAnmat
        brands={brands}
        types={types}
        onChangeBrands={mockOnChangeBrands}
        onChangeTypes={mockOnChangeTypes}
      />
    );

    expect(screen.getByText("Filtrar por")).toBeInTheDocument();

    const filtroMarca = screen.getByTestId("filtro-marca");
    expect(filtroMarca).toHaveTextContent("Brands: BrandA, BrandB");

    const filtroTipo = screen.getByTestId("filtro-tipo");
    expect(filtroTipo).toHaveTextContent("Types: TypeX, TypeY");
  });

  test("CallsOnChangeFunctionsWhenChildButtonsAreClicked", () => {
    const mockOnChangeBrands = jest.fn();
    const mockOnChangeTypes = jest.fn();

    render(
      <FiltroAnmat
        brands={[]}
        types={[]}
        onChangeBrands={mockOnChangeBrands}
        onChangeTypes={mockOnChangeTypes}
      />
    );

    screen.getByTestId("filtro-marca").querySelector("button").click();
    expect(mockOnChangeBrands).toHaveBeenCalledWith(["brand1"]);

    screen.getByTestId("filtro-tipo").querySelector("button").click();
    expect(mockOnChangeTypes).toHaveBeenCalledWith(["type1"]);
  });
});