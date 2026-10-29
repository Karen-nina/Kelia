import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import SelectBrand from "./SelectBrand";

describe("SelectBrand Component", () => {
  test("RendersDefaultOptionInitially", () => {
    render(<SelectBrand />);
    const select = screen.getByRole("combobox");
    expect(select.value).toBe("");
    expect(screen.getByText("Selecciona una marca")).toBeInTheDocument();
  });

  test("ChangesSelectionWhenOptionIsClicked", () => {
    render(<SelectBrand />);
    const select = screen.getByRole("combobox");

    fireEvent.change(select, { target: { value: "2" } });
    expect(select.value).toBe("2");

    const selectedOption = screen.getByRole("option", { name: "Opción 2" });
    expect(selectedOption.selected).toBe(true);
  });
});