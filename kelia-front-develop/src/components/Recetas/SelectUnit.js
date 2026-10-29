import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

const DropdownSelector = () => {
  const mockData = [
  { id: 1, nombre: "Gramo - Gr" },
  { id: 2, nombre: "Kilogramo - Kg" },
  { id: 3, nombre: "Miligramo - Mg" },
  { id: 4, nombre: "Mililitro - Ml" },
  { id: 5, nombre: "Litro - L" },
  { id: 6, nombre: "Cucharada sopera - Cda" },
  { id: 7, nombre: "Cucharadita tipo té - Cdta" },
  { id: 8, nombre: "Taza - Tz" },
  { id: 9, nombre: "Pizca" },
  { id: 10, nombre: "Unidad - U" }
];

  const [selectedId, setSelectedId] = useState("");

  const handleChange = (event) => {
    setSelectedId(event.target.value);
  };

  return (
    <div className="container mt-4">
      <select
        id="selector"
        className="form-select"
        value={selectedId}
        onChange={handleChange}
      >
        <option value="">Selecciona una unidad de medida</option>
        {mockData.map((item) => (
          <option key={item.id} value={item.id}>
            {item.nombre}
          </option>
        ))}
      </select>

    </div>
  );
};

export default DropdownSelector;
