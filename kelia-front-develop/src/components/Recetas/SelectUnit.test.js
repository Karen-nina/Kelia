import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import SelectUnit from "./SelectUnit";

describe("DropdownSelector", () => {
  test("RendersDropdownWithDefaultOption", () => {
    render(<SelectUnit />);
    const defaultOption = screen.getByText("Selecciona una unidad de medida");
    expect(defaultOption).toBeInTheDocument();
  });

  test("RendersAllDropdownOptions", () => {
    render(<SelectUnit />);
    const options = screen.getAllByRole("option");
    expect(options).toHaveLength(11);
    expect(screen.getByText("Gramo - Gr")).toBeInTheDocument();
    expect(screen.getByText("Kilogramo - Kg")).toBeInTheDocument();
    expect(screen.getByText("Pizca")).toBeInTheDocument();
  });

  test("ChangesSelectedOptionOnSelect", () => {
    render(<SelectUnit />);
    const selectElement = screen.getByRole("combobox");

    fireEvent.change(selectElement, { target: { value: "5" } });
    expect(selectElement.value).toBe("5");
  });
});