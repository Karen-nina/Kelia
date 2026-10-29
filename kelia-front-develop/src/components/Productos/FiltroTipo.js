import React from 'react';
import { Form, Button, Spinner } from 'react-bootstrap';
import { useState } from 'react';
import './FiltroyTablas.css';

function FiltroTipo({ types = [], onChange }) {
  const [seleccionados, setSeleccionados] = useState([]);
  const [verMas, setVerMas] = useState(false);

  const handleCheckboxChange = (tipo) => {
    const nuevosSeleccionados = seleccionados.includes(tipo)
      ? seleccionados.filter((t) => t !== tipo)
      : [...seleccionados, tipo];

    setSeleccionados(nuevosSeleccionados);
    if (onChange) {
      onChange(nuevosSeleccionados);
    }
  };

  const tiposAMostrar = verMas ? types : types.slice(0, 10);

  return (
    <Form>
      <h5 className="text-start mb-3">Tipo</h5>

      {types.length === 0 ? (
        <Spinner animation="border" size="sm">
          <span className="visually-hidden">Cargando...</span>
        </Spinner>
      ) : (
        tiposAMostrar.map((tipo, idx) => (
          <Form.Check
            className="text-start"
            key={idx}
            type="checkbox"
            label={tipo}
            checked={seleccionados.includes(tipo)}
            onChange={() => handleCheckboxChange(tipo)}
          />
        ))
      )}

      {types.length > 10 && (
        <div className="ver-masmenos text-start mt-2">
          <Button
            className="button-vermasmenos fw-bold"
            variant="link"
            size="sm"
            onClick={() => setVerMas(!verMas)}
          >
            {verMas ? 'Ver menos' : 'Ver más'}
          </Button>
        </div>
      )}
    </Form>
  );
}

export default FiltroTipo;