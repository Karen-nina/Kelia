import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

const DropdownSelector = () => {
  const mockData = [
  { id: 1, nombre: "1/4" },
  { id: 2, nombre: "1/3" },
  { id: 3, nombre: "1/2" },
  { id: 4, nombre: "2/3" },
  { id: 5, nombre: "3/4" },
  { id: 6, nombre: "1" },
  { id: 7, nombre: "1.5" },
  { id: 8, nombre: "2" },
  { id: 9, nombre: "2.5" },
  { id: 10, nombre: "3" },
  { id: 11, nombre: "4" },
  { id: 12, nombre: "5" },
  { id: 13, nombre: "10" },
  { id: 14, nombre: "50" },
  { id: 15, nombre: "100" },
  { id: 16, nombre: "120" },
  { id: 17, nombre: "150" },
  { id: 18, nombre: "200" },
  { id: 19, nombre: "250" },
  { id: 20, nombre: "500" }
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
        <option value="">Selecciona una cantidad</option>
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
