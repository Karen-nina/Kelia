import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import BuscadorANMAT from "./BuscadorANMAT";

describe("BuscadorANMAT", () => {
  test("RendersSearchBarWithInitialEmptyValue", () => {
    render(<BuscadorANMAT onBuscar={jest.fn()} />);
    const input = screen.getByPlaceholderText(/buscar producto.../i);
    expect(input).toBeInTheDocument();
    expect(input.value).toBe("");
  });

  test("UpdatesInputValueOnChange", () => {
    render(<BuscadorANMAT onBuscar={jest.fn()} />);
    const input = screen.getByPlaceholderText(/buscar producto.../i);
    fireEvent.change(input, { target: { value: "gluten free" } });
    expect(input.value).toBe("gluten free");
  });

  test("CallsOnBuscarWhenEnterIsPressedWithTrimmedInput", () => {
    const onBuscarMock = jest.fn();
    render(<BuscadorANMAT onBuscar={onBuscarMock} />);
    const input = screen.getByPlaceholderText(/buscar producto.../i);

    fireEvent.change(input, { target: { value: "  producto  " } });

    // Presionar Enter
    fireEvent.keyDown(input, { key: "Enter", code: "Enter" });

    expect(onBuscarMock).toHaveBeenCalledTimes(1);
    expect(onBuscarMock).toHaveBeenCalledWith("producto");
  });

  test("DoesNotCallOnBuscarWhenOtherKeyIsPressed", () => {
    const onBuscarMock = jest.fn();
    render(<BuscadorANMAT onBuscar={onBuscarMock} />);
    const input = screen.getByPlaceholderText(/buscar producto.../i);

    fireEvent.change(input, { target: { value: "test" } });
    fireEvent.keyDown(input, { key: "a", code: "KeyA" });

    expect(onBuscarMock).not.toHaveBeenCalled();
  });
});