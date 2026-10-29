import React, { useEffect, useState, useRef } from 'react';
import axios from 'axios';
import RecetaCard from '../../components/Recetas/RecetasTarjetas';
import SearchBar from '../../components/Inicio/SearchBar';
import { FaChevronLeft, FaChevronRight } from 'react-icons/fa';
import './Recetas.css';
import { Link } from 'react-router-dom';
import Spinner from 'react-bootstrap/Spinner';
import EmblaCarousel from '../../components/Shared/EmblaCarousel/EmblaCarousel';
import '../../components/Shared/EmblaCarousel/css/embla.css'


// Constantes para categorías y tags válidos
const CATEGORIAS_VALIDAS = ['almuerzo', 'desayuno', 'cena', 'postre'];
const TAGS_VALIDOS = ['Dulce', 'Salado', 'Vegetariano', 'Picante', 'Horno', 'Rápido'];
const baseUrl = process.env.REACT_APP_BACKEND_URL;

const OPTIONS = { dragFree: true, loop: true }


/* ================================
  sección horizontal de recetas con botones de desplazamiento
================================ */
  const SeccionRecetas = ({ titulo, recetas }) => {

  return (
    <div className="seccion-recetas-container">
      <h2 className="seccion-titulo">{titulo}</h2>
      <EmblaCarousel
            slides={recetas.map((receta, idx) => (<RecetaCard key={idx} receta={receta} />))}
            options={OPTIONS} />
    </div>
  );

};

/* ================================
    receta cruda en formato para la tarjeta
================================ */
const mapearRecetaParaCard = (receta) => ({
  id: receta.id,
  nombre: receta.title,
  imagen: receta.thumbnail,
  autorImg: receta.user?.avatar || 'https://via.placeholder.com/24',
  estrellas: receta.rating || 0,
  tiempo: `${receta.preparationTime} minutos`,
  guardado: receta.save_count > 0,
});

const obtenerRecetasPopulares = (recetas) => {
  return recetas.filter((r) => r.rating >= 4);
};

/* ================================
   Verifica si la receta tiene una categoría válida
================================ */
const recetaTieneCategoriaValida = (categoria) => {
  if (!categoria) return false;
  const categorias = Array.isArray(categoria) ? categoria.map((c) => c.toLowerCase()): [categoria.toLowerCase()];
  return categorias.some((c) => CATEGORIAS_VALIDAS.includes(c));
};

/* ================================
   Agrupa recetas según su categoría válida
================================ */
const agruparPorCategoria = (recetas) => {
  const grupos = {};
  recetas.forEach((receta) => {
    const categoriaRaw = receta.category;
    if (!recetaTieneCategoriaValida(categoriaRaw)) return;

    const categorias = Array.isArray(categoriaRaw) ? categoriaRaw : [categoriaRaw];

    categorias.forEach((cat) => {
      const catLower = cat.toLowerCase();
      if (CATEGORIAS_VALIDAS.includes(catLower)) {
        const nombreCategoria = cat.charAt(0).toUpperCase() + cat.slice(1);
        if (!grupos[nombreCategoria]) {
          grupos[nombreCategoria] = [];
        }
        grupos[nombreCategoria].push(receta);
      }
    });
  });
  return grupos;
};

/* ================================
   Maneja búsqueda, filtros y agrupación de recetas
================================ */
const Recetas = () => {
  const [recetasOriginales, setRecetasOriginales] = useState([]);
  const [recetasFiltradas, setRecetasFiltradas] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const searchInputRef = useRef(null);
  const [tagSeleccionado, setTagSeleccionado] = useState(null);

  // Conecto con json
  const fetchRecetas = async (term = '') => {
    try {
      const response = await axios.get(`${baseUrl}/recipe?term=${term}&q=20`);
      const data = response.data || [];
      setRecetasOriginales(data);
      setRecetasFiltradas(data);
      setIsLoading(false);
    } catch (err) {
      console.error(err);
      setError(err.message);
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchRecetas();
  }, []);

  // Manejador de cambio en campo de búsqueda
  const handleSearchChange = (e) => {
    setSearchTerm(e.target.value);
  };

  // Filtrar recetas por tag seleccionado
  const recetasFiltradasPorTag = tagSeleccionado ? recetasFiltradas.filter((receta) =>
        receta.tags?.some((t) => t.toLowerCase() === tagSeleccionado.toLowerCase())
      ): recetasFiltradas;

  // Agrupar recetas por categoría
  const recetasAgrupadas = agruparPorCategoria(recetasFiltradasPorTag);

  // Ejecutar búsqueda por término ingresado
  const handleSearchSubmit = () => {
    const results = recetasOriginales.filter((receta) =>
      receta.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
      receta.ingredients?.some((ing) => ing.toLowerCase().includes(searchTerm.toLowerCase()))
    );
    setRecetasFiltradas(results);
  };

  // Buscar con tecla Enter
  const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
      fetchRecetas(searchTerm);
    }
  };

  // Estado de carga o error
  if (isLoading) {
  return (
    <div className="d-flex flex-column justify-content-center align-items-center" style={{ height: '200px' }}>
      <p className="mb-3">Cargando receta...</p>
      <Spinner animation="border" role="status">
        <span className="visually-hidden">Cargando...</span>
      </Spinner>
    </div>
  );
}

if (error) {
  return <p>Error: {error}</p>;
}
  return (
    <div className="container">
      <div className="recetas-wrapper">
        {/* Buscador */}
        <div className="buscador-container">

            <SearchBar
              value={searchTerm}
              placeholder="Buscar recetas, ingredientes..."
              onChange={handleSearchChange}
              onKeyDown={handleKeyDown}
            />

        </div>

        {/* Filtros por Tags */}
        <div className="container-tags">
          <div className="tags-izquierda">
            {TAGS_VALIDOS.map((tag) => (
              <button
                key={tag}
                className="tag-button"
                onClick={() => setTagSeleccionado(tag)}
              >
                {tag}
              </button>
            ))}
          </div>

          <div className="limpiar-agregar">
            <button
              className="tag-button limpiar-button"
              onClick={() => setTagSeleccionado(null)}
            >
              ✘ Limpiar filtros 
            </button>

            <Link
              className="tag-button agregar-button"
              to={'nuevareceta'}>
              ✚ Agregar receta
            </Link>
          </div>
        </div>

        {/* Sección Populares */}
        {obtenerRecetasPopulares(recetasFiltradasPorTag).length > 0 && (
          <SeccionRecetas titulo="Populares" recetas={obtenerRecetasPopulares(recetasFiltradasPorTag).map(mapearRecetaParaCard)}
          />
        )}

        {/* Secciones por categoría*/}
        {['Desayuno', 'Almuerzo', 'Cena', 'Postre'].map((categoria) =>
          recetasAgrupadas[categoria] && recetasAgrupadas[categoria].length > 0 ? (
            <SeccionRecetas
              key={categoria}
              titulo={categoria}
              recetas={recetasAgrupadas[categoria].map(mapearRecetaParaCard)}
            />
          ) : null
        )}
      </div>
    </div> 
)};

export default Recetas;