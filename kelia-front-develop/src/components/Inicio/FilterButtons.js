import React from "react";

export const POSTS_FILTER = {
  RECIENTES: "RECIENTES",
  DESTACADOS: "DESTACADOS",
};

const FilterButtons = ({ activeFilter, onFilterChange }) => (
  <div className="mb-4 d-flex justify-content-center gap-3">
    <button
      className={`btn filter-button ${activeFilter === POSTS_FILTER.RECIENTES ? "active" : ""}`}
      onClick={() => onFilterChange(POSTS_FILTER.RECIENTES)}
    >
      Recientes
    </button>
    <button
      className={`btn filter-button ${activeFilter === POSTS_FILTER.DESTACADOS ? "active" : ""}`}
      onClick={() => onFilterChange(POSTS_FILTER.DESTACADOS)}
    >
      Destacados
    </button>
  </div>
);

export default FilterButtons;
