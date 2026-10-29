import { renderContentWithMentions } from "./renderContentWithMentions";

describe("renderContentWithMentions", () => {
  const mentionedData = {
    recipes: [{ id: 1, title: "Tarta" }],
    products: [{ id: 2, title: "Pan" }],
    places: [{ id: 3, name: "Café" }],
  };

  it("devuelve el mismo texto si no hay menciones", () => {
    const result = renderContentWithMentions(
      "Sin menciones",
      null,
      mentionedData
    );
    expect(result).toBe("Sin menciones");
  });

  it("reemplaza menciones de receta por link", () => {
    const result = renderContentWithMentions(
      "Prueba [[recipe:Tarta]]",
      null,
      mentionedData
    );
    expect(result).toContain(
      '<a href="/receta/1" class="mention-link" rel="noopener noreferrer">Tarta</a>'
    );
  });

  it("reemplaza menciones de producto por link", () => {
    const result = renderContentWithMentions(
      "Prueba [[product:Pan]]",
      null,
      mentionedData
    );
    expect(result).toContain(
      '<a href="/productos/2" class="mention-link" rel="noopener noreferrer">Pan</a>'
    );
  });

  it("reemplaza menciones de lugar por link", () => {
    const result = renderContentWithMentions(
      "Prueba [[place:Café]]",
      null,
      mentionedData
    );
    expect(result).toContain(
      '<a href="/establecimiento/3" class="mention-link" rel="noopener noreferrer">Café</a>'
    );
  });

  it("deja el texto si la mención no existe en los datos", () => {
    const result = renderContentWithMentions(
      "Prueba [[recipe:NoExiste]]",
      null,
      mentionedData
    );
    expect(result).toContain("NoExiste");
    expect(result).not.toContain("<a");
  });

  it("resalta el término si highlightTerm está presente", () => {
    const result = renderContentWithMentions(
      "Texto con Pan",
      "Pan",
      mentionedData
    );
    expect(result).toContain("<mark>Pan</mark>");
  });

  it("combina menciones y highlightTerm", () => {
    const result = renderContentWithMentions(
      "[[product:Pan]] y [[place:Café]]",
      "Café",
      mentionedData
    );
    expect(result).toContain(
      '<a href="/productos/2" class="mention-link" rel="noopener noreferrer">Pan</a>'
    );
    expect(result).toContain(
      '<a href="/establecimiento/3" class="mention-link" rel="noopener noreferrer"><mark>Café</mark></a>'
    );
  });
});
