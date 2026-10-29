import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import SelectCantidad from "./SelectCantidad";

describe("SelectCantidad Component", () => {
  test("RendersDefaultOptionInitially", () => {
    render(<SelectCantidad />);
    const select = screen.getByRole("combobox");
    expect(select.value).toBe("");
    expect(screen.getByText("Selecciona una cantidad")).toBeInTheDocument();
  });

  test("ChangesSelectionWhenOptionIsClicked", () => {
    render(<SelectCantidad />);
    const select = screen.getByRole("combobox");

    fireEvent.change(select, { target: { value: "3" } });
    expect(select.value).toBe("3");

    const selectedOption = screen.getByRole("option", { name: "1/2" });
    expect(selectedOption.selected).toBe(true);
  });

  test("RendersAllOptionsCorrectly", () => {
    render(<SelectCantidad />);
    const options = screen.getAllByRole("option");
    expect(options).toHaveLength(21);
    expect(options[0].textContent).toBe("Selecciona una cantidad");
    expect(options[20].textContent).toBe("500");
  });
});