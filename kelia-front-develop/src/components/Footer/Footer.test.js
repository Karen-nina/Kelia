import { render, screen } from "@testing-library/react";
import Footer from "./Footer";
import React from "react";

describe("Footer", () => {
  // Test: Verifica que el footer se renderiza correctamente con el texto de derechos reservados
  it("se renderiza correctamente", () => {
    render(<Footer />);
    expect(
      screen.getByText(/todos los derechos reservados/i)
    ).toBeInTheDocument();
  });

  // Test: Verifica que el enlace a Instagram tiene la URL correcta
  it("muestra el link a Instagram", () => {
    render(<Footer />);
    const link = screen.getAllByRole("link")[0];
    expect(link).toHaveAttribute(
      "href",
      "https://www.instagram.com/kelia.app/"
    );
  });

  // Test: Verifica que el enlace a Facebook tiene la URL correcta
  it("muestra el link a Facebook", () => {
    render(<Footer />);
    const link = screen.getAllByRole("link")[1];
    expect(link).toHaveAttribute("href", "https://www.facebook.com");
  });

  // Test: Verifica que el enlace a X (Twitter) tiene la URL correcta
  it("muestra el link a X (Twitter)", () => {
    render(<Footer />);
    const link = screen.getAllByRole("link")[2];
    expect(link).toHaveAttribute("href", "https://www.x.com");
  });
});
