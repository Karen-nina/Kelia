import React from "react";
import { Container } from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import './FiltroyTablas.css';
import SearchBar from '../Inicio/SearchBar';
import { useState } from "react";

const BuscadorANMAT = ({ onBuscar }) => {
  const [inputValue, setInputValue] = useState("");

  const handleChange = (e) => {
    setInputValue(e.target.value);
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      onBuscar(inputValue.trim());
    }
  };

  return (
    <div className="contenedor-busqueda">
      <Container fluid>
        <SearchBar
          type="text"
          className="busqueda form-control mt-4"
          placeholder=" Buscar producto..."
          value={inputValue}
          onChange={handleChange}
          onKeyDown={handleKeyDown}
        />
      </Container>
    </div>
  );
};

export default BuscadorANMAT;
