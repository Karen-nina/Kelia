import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useGetProductsByType } from "../../hooks/productByType";

const DropdownSelector = ({selectedId, handleChange, typeId}) => {

const { data, error, loading}  = useGetProductsByType(typeId);

  const uniqueByTitle = Array.from(
  new Map(data.map(item => [item.salesName, item])).values()
  );

  return (
    <div className="container mt-4">
      <select
        id="selector"
        className="form-select"
        value={selectedId}
        onChange={handleChange}
      >
        <option value="">Selecciona un producto - marca</option>
        {uniqueByTitle.map((item) => (
          <option key={item.key} value={item.key}>
            {item.salesName} - <strong>{item.brand} </strong>
          </option>
        ))}
      </select>

    </div>
  );
};

export default DropdownSelector;
