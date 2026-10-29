import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import SelectUnitNatural from "./SelectUnitNatural";

describe("DropdownSelector", () => {
  test("RendersDropdownWithDefaultOption", () => {
    render(<SelectUnitNatural />);
    const defaultOption = screen.getByText("Selecciona una unidad de medida");
    expect(defaultOption).toBeInTheDocument();
  });

  test("RendersAllDropdownOptions", () => {
    render(<SelectUnitNatural />);
    const options = screen.getAllByRole("option");

    expect(options).toHaveLength(17);

    expect(screen.getByText("Kilo - Kg")).toBeInTheDocument();
    expect(screen.getByText("Diente (de ajo)")).toBeInTheDocument();
    expect(screen.getByText("Hoja (por ejemplo, espinaca)")).toBeInTheDocument();
  });

  test("ChangesSelectedOptionOnSelect", () => {
    render(<SelectUnitNatural />);
    const selectElement = screen.getByRole("combobox");

    fireEvent.change(selectElement, { target: { value: "14" } });
    expect(selectElement.value).toBe("14");
  });
});