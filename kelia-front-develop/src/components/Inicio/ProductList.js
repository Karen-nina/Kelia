import React from "react";
import { useState } from "react";
import ProductCard from "./ProductCard";
import { Spinner } from "react-bootstrap";

function ProductList({ products, loading, error }) {
  const [mapSeller, setMapSeller] = useState(null);

  if (loading) return <Spinner animation="border" size="sm"><span className="visually-hidden">Cargando...</span></Spinner>;

  if (error) return "Error al buscar los productos...";

  if (products.length == 0)
    return (
      <p className="text-center text-muted">No hay productos disponibles.</p>
    );

  return (
    <>
      {products.map((product, index) => (
        <ProductCard
          key={index}
          {...product}
          onMapClick={() => setMapSeller(product.seller)}
        />
      ))}

      {/* Modal de mapa */}
      {mapSeller && (
        <div className="modal-backdrop" onClick={() => setMapSeller(null)}>
          <div
            className="modal-content"
            style={{ maxWidth: 600, width: "90vw" }}
            onClick={(e) => e.stopPropagation()}
          >
            <button className="close-btn" onClick={() => setMapSeller(null)}>
              ×
            </button>
            <h5 className="mb-3">Ubicación de "{mapSeller}"</h5>
            <iframe
              title="Google Maps"
              width="100%"
              height="350"
              style={{ border: 0, borderRadius: 8 }}
              src={`https://www.google.com/maps?q=${encodeURIComponent(mapSeller)}&output=embed`}
              allowFullScreen
              loading="lazy"
              referrerPolicy="no-referrer-when-downgrade"
            />
          </div>
        </div>
      )}
    </>
  );
}

export default ProductList;
