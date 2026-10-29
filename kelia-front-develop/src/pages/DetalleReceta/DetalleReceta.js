import React from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import "./DetalleReceta.css";
import { FaClock, FaBookmark, FaTimesCircle, FaStar } from "react-icons/fa";
import StarRating from "../../components/StarRating/StarRating";
import RecetaComentario from "../../components/Recetas/RecetaComentario";

const DetalleReceta = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [receta, setReceta] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [change, setChange] = useState(false);

  const baseUrl = process.env.REACT_APP_BACKEND_URL;

  useEffect(() => {
    const fetchReceta = async () => {
      try {
        const response = await fetch(`${baseUrl}/recipe/${id}`);
        if (!response.ok) throw new Error("No se pudo cargar la receta");

        const data = await response.json();
        setReceta(data);
      } catch (err) {
        setError(err.message);
        setReceta(null);
      } finally {
        setLoading(false);
      }
    };

    if (id) {
      setLoading(true);
      fetchReceta();
    }
  }, [id, change]);

  if (loading || error || !receta) return null;

  return (
    <>
      {/*Columna Izquierda*/}
      <div className="detalle-receta">
        <div className="columna-izq">
          <img
            src={receta.thumbnail}
            alt={receta.title}
            className="receta-img"
          />

          <div className="receta-ingredientes">
            <h3 className="ms-3 mt-3">Ingredientes</h3>
            <ul className="text-start">
              {receta.ingredients.map((item, index) => (
                <li className="ingredientes-li" key={index}>
                  <label className="ingredientes-label">{item}</label>
                </li>
              ))}
            </ul>
          </div>

          <div className="ms-3 mt-3">
            <h4>Tags</h4>
            {receta.tags.map((tag, i) => (
              <span key={i} className="badge me-2 mb-2">
                #{tag}
              </span>
            ))}
          </div>
        </div>

        {/*Columna del medio*/}
        <div className="columna-media">
          <h1 className="detalle-receta-titulo">{receta.title}</h1>
          <p className="detalle-receta-autor">
            <img src={receta.user.avatar} alt={receta.user.displayName} />
            <span>
              Por <strong>{receta.user.displayName}</strong> (@
              {receta.user.username})
            </span>
          </p>
          <div>
            <StarRating rating={receta.rating} />
          </div>
          <div className="receta-time">
            <span>
              <FaClock /> {receta.tiempo}
            </span>
            <p className="receta-time"> {receta.preparationTime} minutos</p>
          </div>
          <div className="receta-guardados">
            <FaBookmark color="black" />
            {receta.save_count} guardados
          </div>
          <div className="receta-instrucciones">
            <h3 style={{ marginBottom: 20 }}>Instrucciones</h3>
            <ol style={{ listStyleType: "none", paddingLeft: 0 }}>
              {receta.instructions.map((instruction, index) => (
                <li
                  key={index}
                  style={{
                    display: "flex",
                    alignItems: "center",
                    marginBottom: "10px",
                  }}
                >
                  <span
                    style={{
                      display: "inline-flex",
                      justifyContent: "center",
                      alignItems: "center",
                      width: "32px", // un poco más grande para que el número quede cómodo
                      height: "32px",
                      borderRadius: "50%",
                      backgroundColor: "#d39b63",
                      color: "white",
                      marginRight: "8px",
                      fontSize: "1em",
                      lineHeight: "1", // importante para que el texto no "estire"
                      flexShrink: "0", // por si estás en flex, que no se achique
                      fontWeight: "bold", // negrita
                      fontFamily: '"Poppins", sans-serif',
                    }}
                  >
                    {index + 1}
                  </span>
                  {instruction}
                </li>
              ))}
            </ol>
          </div>
        </div>

        {/*Columna derecha*/}
        <div className="columna-derecha">
          <div className="guardar-reportar-agregar  d-flex align-items-center border rounded px-3 py-2">
            <FaBookmark color="black" />
            <h6 style={{ marginBottom: 0 }}>Guardar receta</h6>
          </div>
          <div className="guardar-reportar-agregar  d-flex align-items-center border rounded px-3 py-2">
            <FaTimesCircle color="black" />
            <h6 style={{ marginBottom: 0 }}>Reportar receta</h6>
          </div>
          <div className="guardar-reportar-agregar  d-flex align-items-center border rounded px-3 py-2">
            <FaStar color="gold" />
            <h6 style={{ marginBottom: 0 }}>Agregar reseña</h6>
          </div>
        </div>
      </div>
      {/* Botón Volver alineado a la derecha */}
      <div className="btn-volver-container">
        <button className="btn-volver" onClick={() => navigate(-1)}>
          Volver
        </button>
      </div>
      {/* COMENTARIOS: fuera del contenedor principal */}
      <div className="contenedor-comentarios mb-4">
        {receta.reviews && receta.reviews.length > 0 ? (
          receta.reviews.map((review, idx) => (
            <div className="comentario" key={idx}>
              <div className="comentario-autor">
                <img
                  src={review.user?.avatar || "/img/avatars/default.png"}
                  alt={review.user?.displayName}
                  className="foto-perfil-comentario"
                />
                <div className="info-autor">
                  <p className="nombre-autor">{review.user?.displayName}</p>
                  <div>
                    <StarRating rating={review.rating} />
                  </div>
                </div>
              </div>
              <div className="comentario-texto">
                <p>{review.comment}</p>
              </div>
              <p className="comentario-tiempo">{review.timeAgo || ""}</p>
            </div>
          ))
        ) : (
          <>
            <p className="text-center">¡Sé el primero en dejar una reseña!</p>
          </>
        )}
        <RecetaComentario
          recipeId={receta.id}
          onCommentAdded={() => setChange(!change)}
        />
      </div>
    </>
  );
};

export default DetalleReceta;
