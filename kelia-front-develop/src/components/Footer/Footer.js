import React from "react";
import "./Footer.css";

const Footer = () => {
  return (
    <footer
      className=" footer bg-light py-4 mt-auto"
      style={{ backgroundColor: "#f7f7f0" }}
    >
      <div className="container d-flex flex-column flex-md-row justify-content-between align-items-center">
        <p
          className="mb-3 mb-md-0"
          style={{ color: "#626a38", fontWeight: 400 }}
        >
          Todos los derechos reservados © 2025
        </p>
        <div className="d-flex gap-4">
          <a
            href="https://www.instagram.com/kelia.app/"
            target="_blank"
            rel="noopener noreferrer"
          >
            <i
              className="bi bi-instagram"
              style={{ fontSize: "1.5rem", color: "#9BB084" }}
            ></i>
          </a>
          <a
            href="https://www.facebook.com"
            target="_blank"
            rel="noopener noreferrer"
          >
            <i
              className="bi bi-facebook"
              style={{ fontSize: "1.5rem", color: "#9BB084" }}
            ></i>
          </a>
          <a href="https://www.x.com" target="_blank" rel="noopener noreferrer">
            <i
              className="bi bi-twitter-x"
              style={{ fontSize: "1.5rem", color: "#9BB084" }}
            ></i>
          </a>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
