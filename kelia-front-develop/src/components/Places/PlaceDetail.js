import React from 'react';
import StarRating from '../StarRating/StarRating';

  const PlaceDetail = ({ lugar, apiError, apiData, onClose }) => {
  
  if (!lugar) return null;

  const iconSrc = `/img/markers/${lugar.category?.toLowerCase() ?? 'default'}.png`;

  return (
    <div className="card position-relative" style={{ width: '100%' }}>
      <button
        onClick={onClose}
        aria-label="Cerrar"
        className="btn btn-link position-absolute top-0 end-0 m-2 p-0"
        style={{
          fontSize: '1.25rem',
          lineHeight: '1',
          height: 'auto',
          textDecoration: 'none',
          color: 'black',
          background: 'transparent',
          border: 'none',
        }}
      >
        ×
      </button>

      <div className="card-body">
        <div className="d-flex align-items-center mb-0">
          <h5 className="card-title mb-0">{lugar.name}</h5>
        </div>
        <div className="d-flex mb-2 align-self-end">
          <h6 className="card-subtitle mb-0 text-muted align-self-end">{lugar.category}</h6>
          <img
            src={iconSrc}
            alt={lugar.category}
            width={20}
            height={20}
            className="ms-1"
          />
        </div>
        <div className="mb-2 lh-sm">
              <div className="d-flex align-items-center">
                <StarRating rating={lugar.rating} />
                <small className="text-muted">({lugar.rating})</small>
              </div>
              <small className="text-muted">Total de reseñas: {lugar.reviews?.length ?? 0}</small>
            </div>
        <div className="d-flex align-items-center mt-3">
          <span className="text-muted me-2">Valorado por</span>
          <img
            src="/img/logos/kelia.png"
            alt="logo-kelia"
            style={{ width: 'auto', height: 24 }}
          />
        </div>

        {!apiError && apiData && (
          <>
            <hr />

            <div className="mb-2 lh-sm">
              <div className="d-flex align-items-center">
                <StarRating rating={apiData.rating} />
                <small className="text-muted">({apiData.rating})</small>
              </div>
              <small className="text-muted">Total de reseñas: {apiData.totalUserRatings}</small>
            </div>

            <div className="d-flex align-items-center mt-3">
              <span className="text-muted me-2">Valorado por</span>
              <img
                src="/img/logos/google-maps.png"
                alt="logo-google-maps"
                style={{ width: 'auto', height: 24 }}
              />
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default PlaceDetail;