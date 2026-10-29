import React from 'react';
import { Button, Form, Spinner, Alert } from 'react-bootstrap'; 
import { useState } from 'react';
import './FiltroyTablas.css';

function FiltroMarca({ brands = [], onChange }) {
  const [seleccionadas, setSeleccionadas] = useState([]);
  const [verMas, setVerMas] = useState(false);

  const handleCheckboxChange = (marca) => {
    const nuevasSeleccionadas = seleccionadas.includes(marca)
      ? seleccionadas.filter((m) => m !== marca)
      : [...seleccionadas, marca];

    setSeleccionadas(nuevasSeleccionadas);
    if (onChange) {
      onChange(nuevasSeleccionadas);
    }
  };

  const marcasAMostrar = verMas ? brands : brands.slice(0, 10);

  return (
    <Form>
      <h5 className="text-start mb-3">Marca</h5>

      {brands.length === 0 ? (
        <Spinner animation="border" size="sm">
          <span className="visually-hidden">Cargando...</span>
        </Spinner>
      ) : (
        marcasAMostrar.map((marca) => (
          <Form.Check
            className="text-start"
            key={marca}
            type="checkbox"
            label={marca}
            checked={seleccionadas.includes(marca)}
            onChange={() => handleCheckboxChange(marca)}
          />
        ))
      )}

      {brands.length > 10 && (
        <div className="ver-masmenos text-start mt-2">
          <Button
            className="button-vermasmenos fw-bold"
            variant="link"
            size="sm"
            onClick={() => setVerMas(!verMas)}>
            {verMas ? 'Ver menos' : 'Ver más'}
          </Button>
        </div>
      )}
    </Form>
  );
}

export default FiltroMarca;