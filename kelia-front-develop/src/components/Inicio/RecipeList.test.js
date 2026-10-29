import React from "react";
import { render, screen } from "@testing-library/react";
import RecipeList from "./RecipeList";

jest.mock("./RecipeCard", () => (props) => (
  <div data-testid="recipe-card">{props.title || "RecipeCard"}</div>
));

describe("RecipeList Component", () => {
  test("ShowsSpinnerWhenLoadingIsTrue", () => {
    render(<RecipeList recipes={[]} loading={true} />);
    expect(screen.getByText(/cargando/i)).toBeInTheDocument();
  });

  test("ShowsNoRecipesMessageWhenRecipesArrayIsEmptyAndNotLoading", () => {
    render(<RecipeList recipes={[]} loading={false} />);
    expect(screen.getByText(/No hay receta disponibles/i)).toBeInTheDocument();
  });

  test("RendersAListOfRecipeCardComponentsWhenRecipesArePresent", () => {
    const recipes = [
      { id: "1", title: "Recipe 1", thumbnail: "", tags: [] },
      { id: "2", title: "Recipe 2", thumbnail: "", tags: [] },
    ];
    render(<RecipeList recipes={recipes} loading={false} />);
    const cards = screen.getAllByTestId("recipe-card");
    expect(cards).toHaveLength(2);
    expect(cards[0]).toHaveTextContent("Recipe 1");
    expect(cards[1]).toHaveTextContent("Recipe 2");
  });
});