import React from 'react';
import { Link } from 'react-router-dom';

const RecipeCard = ({ id, thumbnail, title, tags }) => {
  return (
    <Link
      to={`/receta/${id}`}
      style={{ textDecoration: 'none', color: 'inherit' }}
    >
      <div className="recipe-card">
        <img
          src={thumbnail}
          alt={title}
          style={{
            width: '100%',
            borderRadius: '10px',
            objectFit: 'cover',
            marginBottom: '10px',
          }}
        />
        <h5 style={{ fontSize: '1rem', fontWeight: 'bold', marginBottom: '10px' }}>
          {title}
        </h5>
        <p style={{ fontSize: '0.9rem', color: '#888' }}>
          {tags.join(' | ')}
        </p>
      </div>
    </Link>
  );
};

export default RecipeCard;