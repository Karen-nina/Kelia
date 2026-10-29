import React, { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import "./Navbar.css";

const Navbar = () => {
  const { user, userData, isFullyRegistered, logout } = useAuth();
  const navigate = useNavigate();
  const [isExpanded, setIsExpanded] = useState(false);

  const handleLogout = async () => {
    try {
      await logout();
      localStorage.removeItem("token");
    } catch (err) {
      console.error("Error al cerrar sesión:", err);
    }
  };

  const toggleMenu = () => setIsExpanded(!isExpanded);
  const isUserRegistered = user && isFullyRegistered;

  return (
    <nav className="navbar navbar-expand-lg bg-white py-1 fixed-top shadow-sm">
      <div className="container">
        <a href="/" className="navbar-brand">
          <img src="/img/logo.png" alt="Kelia logo" width="130" />
        </a>

        <button
          className="navbar-toggler"
          type="button"
          onClick={toggleMenu}
          aria-controls="navbarNav"
          aria-expanded={isExpanded}
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div
          className={`collapse navbar-collapse ${isExpanded ? "show" : ""}`}
          id="navbarNav"
        >
          <ul className="navbar-nav mx-auto gap-lg-4 text-center">
            <li className="nav-item">
              <NavLink to="/inicio" className="nav-link" onClick={() => setIsExpanded(false)}>Inicio</NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/recetas" className="nav-link" onClick={() => setIsExpanded(false)}>Recetas</NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/establecimientos" className="nav-link" onClick={() => setIsExpanded(false)}>Establecimientos</NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/productos" className="nav-link" onClick={() => setIsExpanded(false)}>Productos</NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/faq" className="nav-link" onClick={() => setIsExpanded(false)}>FAQs</NavLink>
            </li>

            {isUserRegistered ? (
              <li className="nav-item d-lg-none">
                <button
                  className="button-cerrar btn btn-outline-secondary mt-2 px-4 mx-auto d-block"
                  onClick={() => {
                    setIsExpanded(false);
                    handleLogout();
                  }}
                >
                  Cerrar sesión
                </button>
              </li>
            ) : (
              <li className="nav-item d-lg-none">
                <button
                  className="button-inicio btn btn-outline-success mt-2 px-4 mx-auto d-block"
                  onClick={() => {
                    setIsExpanded(false);
                    navigate("/login");
                  }}
                >
                  Iniciar sesión
                </button>
              </li>
            )}
          </ul>
        </div>

        <div className="d-none d-lg-flex align-items-center gap-3">
          {isUserRegistered ? (
            <>
              <i className="bi bi-bell" style={{ fontSize: "1.5rem", color: "#9BB084" }}></i>
              <img
                src={userData?.avatar || "/img/avatars/default.png"}
                alt="Icono usuario"
                style={{
                  width: "2rem",
                  height: "2rem",
                  borderRadius: "50%",
                  objectFit: "cover",
                  border: "2px solid #9BB084",
                }}
              />
              <button
                className="button-cerrar btn btn-outline-secondary btn-sm"
                onClick={handleLogout}
              >
                Cerrar sesión
              </button>
            </>
          ) : (
            <button
              className="button-inicio btn btn-outline-success btn-sm"
              onClick={() => navigate("/login")}
            >
              Iniciar sesión
            </button>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;