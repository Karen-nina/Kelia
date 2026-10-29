import React from "react";
import { MemoryRouter } from "react-router-dom";
import { render, screen } from "@testing-library/react";
import { generateMentionPills } from "./generateMentionPills";

describe("generateMentionPills", () => {
  function renderPills(props, maxTotal) {
    // generateMentionPills devuelve un array de elementos React
    // Los renderizamos dentro de un MemoryRouter para que los <Link> funcionen
    return render(
      <MemoryRouter>{generateMentionPills(props, maxTotal)}</MemoryRouter>
    );
  }

  it("devuelve array vacío si no hay datos", () => {
    expect(generateMentionPills({})).toEqual([]);
  });

  it("renderiza recetas correctamente", () => {
    renderPills({ recipes: [{ id: 1, title: "Tarta" }] });
    expect(screen.getByText("Tarta")).toBeInTheDocument();
    expect(screen.getByText("🥣")).toBeInTheDocument();
  });

  it("renderiza productos correctamente", () => {
    renderPills({ products: [{ id: 2, title: "Pan" }] });
    expect(screen.getByText("Pan")).toBeInTheDocument();
    expect(screen.getByText("🛒")).toBeInTheDocument();
  });

  it("renderiza lugares correctamente", () => {
    renderPills({ places: [{ id: 3, name: "Café" }] });
    expect(screen.getByText("Café")).toBeInTheDocument();
    expect(screen.getByText("📍")).toBeInTheDocument();
  });

  it('muestra "+N más" si hay más de maxTotal', () => {
    const recipes = [
      { id: 1, title: "Tarta" },
      { id: 2, title: "Pan" },
      { id: 3, title: "Pizza" },
      { id: 4, title: "Empanada" },
    ];
    renderPills({ recipes }, 2);
    expect(screen.getByText("Tarta")).toBeInTheDocument();
    expect(screen.getByText("Pan")).toBeInTheDocument();
    expect(screen.getByText("+2 más")).toBeInTheDocument();
  });

  it("mezcla tipos y respeta el límite", () => {
    const recipes = [{ id: 1, title: "Tarta" }];
    const products = [{ id: 2, title: "Pan" }];
    const places = [{ id: 3, name: "Café" }];
    renderPills({ recipes, products, places }, 2);
    // Solo deben aparecer dos elementos y el +1 más
    expect(screen.getByText("Tarta")).toBeInTheDocument();
    expect(screen.getByText("Pan")).toBeInTheDocument();
    expect(screen.getByText("+1 más")).toBeInTheDocument();
  });
});
