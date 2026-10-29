import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

const DropdownSelector = () => {
  const mockData = [
  { id: 1, nombre: "Kilo - Kg" },
  { id: 2, nombre: "Gramo - Gr" },
  { id: 3, nombre: "Miligramo - Mg" },
  { id: 4, nombre: "Litro - L" },
  { id: 5, nombre: "Mililitro - Ml" },
  { id: 6, nombre: "Cucharada sopera - Cda" },
  { id: 7, nombre: "Cucharadita tipo té - Cdta" },
  { id: 8, nombre: "Taza - Tz" },
  { id: 9, nombre: "Puñado" },
  { id: 10, nombre: "Rodaja" },
  { id: 11, nombre: "Diente (de ajo)" },
  { id: 12, nombre: "Hoja (por ejemplo, espinaca)" },
  { id: 13, nombre: "Pizca" },
  { id: 14, nombre: "Ralladura" },
  { id: 15, nombre: "Jugo" },
  { id: 16, nombre: "Unidad - U" }
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
