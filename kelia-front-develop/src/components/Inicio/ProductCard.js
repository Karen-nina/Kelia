import React from "react";

const ProductCard = ({
  title,
  thumbnail,
  link,
  seller,
  certificate,
  onMapClick,
}) => {
  return (
    <div
      className="product-card"
      style={{
        backgroundColor: "#fff",
        borderRadius: "10px",
        padding: "15px",
        boxShadow: "0px 4px 6px rgba(0, 0, 0, 0.1)",
      }}
    >
      <div style={{ position: "relative", textAlign: "center" }}>
        <img
          src={thumbnail}
          alt={title}
          style={{
            width: "auto",
            borderRadius: "10px",
            objectFit: "cover",
            marginBottom: "10px",
            maxHeight: "160px",
          }}
        />
        {certificate && (
          <span
            style={{
              position: "absolute",
              top: "8px",
              right: "8px",
              backgroundColor: "#ffffff",
              color: "#28a745",
              border: "1px solid #28a745",
              borderRadius: "6px",
              padding: "2px 6px",
              fontSize: "9px",
              fontWeight: "bold",
              lineHeight: "1.1",
              textAlign: "center",
              whiteSpace: "pre-line",
              boxShadow: "0 0 6px 4px #ffffff",
              zIndex: 2,
            }}
            title="Vendedor verificado"
          >
            Aprobado{"\n"}por ANMAT
          </span>
        )}
      </div>

      <h5
        style={{ fontSize: "1rem", fontWeight: "bold", marginBottom: "10px" }}
      >
        {title}
      </h5>
      <p style={{ fontSize: "0.9rem", color: "#888", marginBottom: "15px" }}>
        Vendido por: {seller}
      </p>
      <div style={{ display: "flex", justifyContent: "center", gap: "10px" }}>
        <a
          href={link}
          target="_blank"
          rel="noopener noreferrer"
          className="btn product-button meli-buy-button"
          style={{ display: "flex", alignItems: "center", gap: 6 }}
        >
          <img
            src="/img/MELI.png"
            alt="Mercado Libre"
            style={{
              width: 22,
              height: 22,
              objectFit: "contain",
              borderRadius: 4,
              padding: 2,
            }}
          />
          Comprar
        </a>
        <button className="btn product-button" onClick={onMapClick}>
          Ver mapa
        </button>
      </div>
    </div>
  );
};

export default ProductCard;
