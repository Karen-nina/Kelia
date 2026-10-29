import React, { useState } from "react";
import { FormCheck, FormControl, ListGroup } from "react-bootstrap";

export default function SearchControl({ items, onChange }) {
  const [busqueda, setBusqueda] = useState("");
  const [seleccionados, setSeleccionados] = useState([]);

  const filtrarItems = () => {
    if (!busqueda) {
      //setSeleccionados([]);
      //onChange([])
      return [];
    }
    return items.filter((item) =>
      item.title.toLowerCase().includes(busqueda.toLowerCase())
    );
  };

  const toggleSeleccion = (item) => {
    const actualizados = seleccionados.includes(item.key)
      ? seleccionados.filter((i) => i !== item.key)
      : [...seleccionados, item.key];

    setSeleccionados(actualizados);
    const titles = items
      .filter((it) => actualizados.includes(it.key))
      .map((im) => im.title);
    onChange(titles); // Notifica al padre
  };

  const resaltarCoincidencia = (texto) => {
    const i = texto.toLowerCase().indexOf(busqueda.toLowerCase());
    if (i === -1) return texto;

    const antes = texto.slice(0, i);
    const match = texto.slice(i, i + busqueda.length);
    const despues = texto.slice(i + busqueda.length);

    return (
      <>
        {antes}
        <span style={{ backgroundColor: "yellow" }}>{match}</span>
        {despues}
      </>
    );
  };

  return (
    <div style={{ maxWidth: "400px", margin: "20px auto" }}>
      <FormControl
        type="text"
        placeholder="Buscar marca..."
        value={busqueda}
        onChange={(e) => setBusqueda(e.target.value)}
      />
      <ListGroup className="mt-2">
        {filtrarItems().map((item, index) => (
          <ListGroup.Item
            key={item.key}
            action
            onClick={() => toggleSeleccion(item)}
            active={seleccionados.includes(item.key)}
          >
            <FormCheck
              inline
              checked={seleccionados.includes(item.key)}
              onChange={() => toggleSeleccion(item)}
              label={resaltarCoincidencia(item.title)}
            />
          </ListGroup.Item>
        ))}
      </ListGroup>
    </div>
  );
}
