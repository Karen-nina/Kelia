import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import ProductList from "./ProductList";

// Mock ProductCard para simplificar
jest.mock("./ProductCard", () => (props) => (
  <div data-testid="product-card" onClick={props.onMapClick}>
    {props.name || "ProductCard"}
  </div>
));

describe("ProductList Component", () => {
  test("ShowsSpinnerWhenLoadingIsTrue", () => {
    render(<ProductList products={[]} loading={true} error={null} />);
    expect(screen.getByText(/cargando/i)).toBeInTheDocument();
  });

  test("ShowsErrorMessageWhenErrorExists", () => {
    render(<ProductList products={[]} loading={false} error="Some error" />);
    expect(screen.getByText(/Error al buscar los productos/i)).toBeInTheDocument();
  });

  test("ShowsNoProductsMessageWhenProductsArrayIsEmptyAndNoErrorOrLoading", () => {
    render(<ProductList products={[]} loading={false} error={null} />);
    expect(screen.getByText(/No hay productos disponibles/i)).toBeInTheDocument();
  });

  test("RendersListOfProductCards", () => {
    const products = [
      { name: "Product 1", seller: "Seller A" },
      { name: "Product 2", seller: "Seller B" },
    ];

    render(<ProductList products={products} loading={false} error={null} />);
    const cards = screen.getAllByTestId("product-card");
    expect(cards).toHaveLength(2);
    expect(cards[0]).toHaveTextContent("Product 1");
    expect(cards[1]).toHaveTextContent("Product 2");
  });

  test("OpensAndClosesModalOnMapSellerClickAndModalInteractions", () => {
    const products = [
      { name: "Product 1", seller: "Seller A" },
    ];

    render(<ProductList products={products} loading={false} error={null} />);

    // Initially, modal should not be in the document
    expect(screen.queryByText(/Ubicación de/)).not.toBeInTheDocument();

    // Click on product opens modal with seller info
    fireEvent.click(screen.getByTestId("product-card"));

    expect(screen.getByText(/Ubicación de "Seller A"/)).toBeInTheDocument();

    const iframe = screen.getByTitle("Google Maps");
    expect(iframe).toHaveAttribute(
      "src",
      expect.stringContaining(encodeURIComponent("Seller A"))
    );

    // Click on backdrop closes modal
    fireEvent.click(screen.getByText(/Ubicación de "Seller A"/).parentElement.parentElement);

    expect(screen.queryByText(/Ubicación de/)).not.toBeInTheDocument();
  });

  test("ClickingCloseButtonClosesModal", () => {
    const products = [
      { name: "Product 1", seller: "Seller A" },
    ];

    render(<ProductList products={products} loading={false} error={null} />);

    fireEvent.click(screen.getByTestId("product-card"));
    expect(screen.getByText(/Ubicación de "Seller A"/)).toBeInTheDocument();

    const closeBtn = screen.getByText("×");
    fireEvent.click(closeBtn);

    expect(screen.queryByText(/Ubicación de/)).not.toBeInTheDocument();
  });
});