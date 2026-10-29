// src/components/RecetaCard.jsx
import React from 'react';
import { FaClock, FaBookmark } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import './RecetasTarjetas.css';

const RecetaCard = ({ receta }) => {
  return (
  <Link
      to={`/receta/${receta.id}`}
      style={{ textDecoration: 'none', color: 'inherit' }}
    >
  <div className="receta-card">
    <div className="receta-img-wrapper">
      <img src={receta.imagen} alt={receta.nombre} className="receta-card-img" />
      <img src={receta.autorImg} alt="Autor" className="receta-autor" />
    </div>
    <div className="receta-info">
      <div className="receta-titulo">{receta.nombre}</div>
      <div className="receta-meta">
        <div className="receta-estrellas">
          {'★'.repeat(receta.estrellas)}{'☆'.repeat(5 - receta.estrellas)}
        </div>
      </div>
      <div className="receta-footer">
        <span><FaClock /> {receta.tiempo}</span>
        <FaBookmark color={receta.guardado ? 'black' : 'white'} />
      </div>
    </div>
  </div>
  </Link>
);

};

export default RecetaCard;