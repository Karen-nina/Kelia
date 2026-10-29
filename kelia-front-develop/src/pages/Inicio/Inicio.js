import React, { useState } from "react";
import SearchBar from "../../components/Inicio/SearchBar";
import FilterButtons, {
  POSTS_FILTER,
} from "../../components/Inicio/FilterButtons";
import PostForm from "../../components/Inicio/PostForm";

import "./Inicio.css";
import { useGetProducts } from "../../hooks/useProducts";
import { useGetPosts } from "../../hooks/usePosts";
import ProductList from "../../components/Inicio/ProductList";
import PostList from "../../components/Inicio/PostList";
import RecipeList from "../../components/Inicio/RecipeList";
import { useGetRecipes } from "../../hooks/useRecipes";

const Inicio = () => {
  // Numero de likes para considerarse destacado
  const COUNT_LIKES = 3;

  // Esados de termnino de busqueda, seleccionado y filtro de posts
  const [searchTerm, setSearchTerm] = useState("");
  const [isFocused, setIsFocused] = useState(false);
  const [activeFilter, setActiveFilter] = useState(POSTS_FILTER.RECIENTES);

  // Busqueda de productos
  const {
    data: productsData,
    error: productsError,
    loading: productsLoading,
  } = useGetProducts(searchTerm);

  // Busqueda de posts
  const {
    data: postsData,
    error: postError,
    loading: postsLoading,
  } = useGetPosts(searchTerm, activeFilter);

  // Busqueda de recetas
  const {
    data: recipesData,
    error: recipiesError,
    loading: recipesLoading,
  } = useGetRecipes(searchTerm);

  // Manejador de ingreso de terminos y presion de enter.
  const handleSearch = async (e) => {
    if (e.key === "Enter") {
      try {
        setSearchTerm(e.target.value);
      } catch (error) {
        console.error("Error al buscar " + error);
      }
    }
  };

  // Manejador de filtro de seleccionado
  const handleFilter = (filter) => {
    setActiveFilter(filter);
    setSearchTerm("");
  };

  // Manejadore de filtros de tipos de posts.
  const postsFilter = (data) => {
    if (activeFilter === POSTS_FILTER.DESTACADOS) {
      return data.filter(
        (post) => post.likes && post.likes.length > COUNT_LIKES
      );
    } else {
      return data.slice().sort((a, b) => new Date(b.date) - new Date(a.date));
    }
  };

  return (
    <div className="container">
      <div className=" inicio-wrapper">
        {/* Barra de búsqueda ocupando todo el ancho */}
        <div className="my-4">
          <SearchBar
            placeholder="Buscar productos, recetas, publicaciones..."
            onKeyDown={(e) => handleSearch(e)}
          />
        </div>

        <div className="row justify-content-center">
          {/* Columna izquierda para la tarjeta del producto */}
          <div className="col-12 col-md-3 mb-4">
            <div className="custom-sidebar mt-2">
              <h4 className="text-center mb-3 text-secondary">Productos</h4>
              <ProductList
                products={productsData}
                loading={productsLoading}
                error={productsError}
              />
            </div>
          </div>

          {/* Columna central: publicaciones */}
          <div className="col-12 col-md-8 col-lg-6 mt-4">
            <PostForm isFocused={isFocused} setIsFocused={setIsFocused} />
            <FilterButtons
              activeFilter={activeFilter}
              onFilterChange={handleFilter}
            />
            <PostList
              posts={postsFilter(postsData)}
              loading={postsLoading}
              highlightTerm={searchTerm}
            ></PostList>
          </div>

          {/* Columna derecha para recetas */}
          <div className="col-12 col-md-3">
            <div className="custom-sidebar mt-2">
              <h4 className="text-center mb-3 text-secondary">Recetas</h4>
              <RecipeList recipes={recipesData} loading={recipesLoading} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Inicio;
