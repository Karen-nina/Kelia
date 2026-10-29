import { render, screen, fireEvent } from "@testing-library/react";
import EmblaCarousel from "./EmblaCarousel";
import React from "react";

// Mock de embla-carousel-react para simular la funcionalidad del carousel
jest.mock("embla-carousel-react", () => () => [jest.fn(), {}]);

// Mock de los componentes de botones de navegación (anterior/siguiente)
jest.mock("./EmblaCarouselArrowButtons", () => ({
  PrevButton: (props) => (
    <button data-testid="prev" {...props}>
      Prev
    </button>
  ),
  NextButton: (props) => (
    <button data-testid="next" {...props}>
      Next
    </button>
  ),
  usePrevNextButtons: () => ({
    prevBtnDisabled: false,
    nextBtnDisabled: true,
    onPrevButtonClick: jest.fn(),
    onNextButtonClick: jest.fn(),
  }),
}));

// Mock de los componentes de botones de puntos (dots)
jest.mock("./EmblaCarouselDotButton", () => ({
  DotButton: (props) => (
    <button data-testid="dot" {...props}>
      Dot
    </button>
  ),
  useDotButton: () => ({
    selectedIndex: 0,
    scrollSnaps: [0, 1, 2],
    onDotButtonClick: jest.fn(),
  }),
}));

describe("EmblaCarousel", () => {
  // Test: Verifica que el carousel renderiza correctamente todos los slides proporcionados
  it("renderiza los slides correctamente", () => {
    render(<EmblaCarousel slides={[1, 2, 3]} options={{}} />);
    expect(screen.getAllByText(/\d/).length).toBe(3);
  });

  // Test: Verifica que se renderizan los botones de navegación y los puntos indicadores
  it("renderiza los botones de navegación y dots", () => {
    render(<EmblaCarousel slides={[1, 2, 3]} options={{}} />);
    expect(screen.getByTestId("prev")).toBeInTheDocument();
    expect(screen.getByTestId("next")).toBeInTheDocument();
    expect(screen.getAllByTestId("dot").length).toBe(3);
  });

  // Test: Verifica que los botones de navegación tienen el estado correcto (habilitado/deshabilitado)
  it("el botón next está deshabilitado y prev está habilitado según el estado", () => {
    render(<EmblaCarousel slides={[1, 2, 3]} options={{}} />);
    expect(screen.getByTestId("next")).toBeDisabled();
    expect(screen.getByTestId("prev")).not.toBeDisabled();
  });
});
