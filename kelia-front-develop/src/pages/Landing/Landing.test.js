import { render, screen } from "@testing-library/react";
import Landing from "./Landing";
import React from "react";
import { BrowserRouter } from "react-router-dom";

describe("Landing", () => {
  // Test: Verifica que la página de landing se renderiza correctamente y muestra el contenido principal
  it("renderiza sin errores y muestra el título principal", () => {
    render(
      <BrowserRouter>
        <Landing />
      </BrowserRouter>
    );
    // Verifica el texto principal del hero (mensaje principal de la aplicación)
    expect(screen.getByText(/sin gluten, sin miedo/i)).toBeInTheDocument();
    // Verifica el subtítulo que explica el propósito de la aplicación
    expect(screen.getByText(/comunidad celíaca necesita/i)).toBeInTheDocument();
  });
});
