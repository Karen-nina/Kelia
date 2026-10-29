import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

const DropdownSelector = () => {
  const mockData = [
    { id: 1, nombre: "Opción 1" },
    { id: 2, nombre: "Opción 2" },
    { id: 3, nombre: "Opción 3" }
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
        <option value="">Selecciona una marca</option>
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
