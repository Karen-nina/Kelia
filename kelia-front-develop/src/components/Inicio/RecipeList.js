import React from "react";
import RecipeCard from "./RecipeCard";
import { Spinner } from "react-bootstrap";

function RecipeList({ recipes, loading }) {
  if (loading) return <Spinner animation="border" size="sm"><span className="visually-hidden">Cargando...</span></Spinner>;

  if (recipes.length == 0)
    return <p className="text-center text-muted">No hay receta disponibles.</p>;

  return recipes.map((recipe, index) => <RecipeCard key={index} {...recipe} />);
}

export default RecipeList;
