import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useGetTypes } from "../../hooks/type";

const DropdownSelector = ({selectedId, handleChange}) => {
  const mockData = [
    { id: 1, nombre: "Opción 1" },
    { id: 2, nombre: "Opción 2" },
    { id: 3, nombre: "Opción 3" }
  ];

  const { data, error, loading}  = useGetTypes(1);



  return (
    <div className="container mt-4">
      <select
        id="selector"
        className="form-select"
        value={selectedId}
        onChange={handleChange}
      >
        <option value="">Selecciona un tipo de producto</option>
        {data.map((item) => (
          <option key={item.key} value={item.title}>
            {item.title}
          </option>
        ))}
      </select>

    </div>
  );
};

export default DropdownSelector;
