import React from "react";
import { render, screen } from "@testing-library/react";
import TablaAnmat from "./TablaAnmat";

describe("TablaAnmat", () => {
  test("RendersTableHeaderCorrectly", () => {
    render(<TablaAnmat products={[]} />);
    expect(screen.getByText("Nombre")).toBeInTheDocument();
    expect(screen.getByText("Marca")).toBeInTheDocument();
    expect(screen.getByText("Tipo")).toBeInTheDocument();
  });

  test("RendersUniqueProductsWithoutDuplicates", () => {
    const products = [
      { id: "1", salesName: "Product A", brand: "Brand X", productType: "Type 1" },
      { id: "2", salesName: "Product A", brand: "Brand X", productType: "Type 1" },
      { id: "3", salesName: "Product B", brand: "Brand Y", productType: "Type 2" },
    ];

    render(<TablaAnmat products={products} />);

    const rows = screen.getAllByRole("row");
    expect(rows).toHaveLength(3);

    expect(screen.getByText("Product A")).toBeInTheDocument();
    expect(screen.getByText("Brand X")).toBeInTheDocument();
    expect(screen.getByText("Type 1")).toBeInTheDocument();

    expect(screen.getByText("Product B")).toBeInTheDocument();
    expect(screen.getByText("Brand Y")).toBeInTheDocument();
    expect(screen.getByText("Type 2")).toBeInTheDocument();
  });

  test("RendersEmptyTableWhenNoProducts", () => {
    render(<TablaAnmat products={[]} />);
    const rows = screen.getAllByRole("row");
    expect(rows).toHaveLength(1);
  });
});