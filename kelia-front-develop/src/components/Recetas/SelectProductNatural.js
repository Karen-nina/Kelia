import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

const DropdownSelector = () => {
  const mockData = [
    { id: 1, nombre: "Huevo" },
    { id: 2, nombre: "Banana" },
    { id: 3, nombre: "Manzana roja" },
    { id: 4, nombre: "Frutilla" },
    { id: 5, nombre: "Leche" },
    { id: 6, nombre: "Miel" },
    { id: 7, nombre: "Espinaca" },
    { id: 8, nombre: "Zanahoria" },
    { id: 9, nombre: "Papa" },
    { id: 10, nombre: "Batata" },
    { id: 11, nombre: "Tomate" },
    { id: 12, nombre: "Cebolla" },
    { id: 13, nombre: "Ajo" },
    { id: 14, nombre: "Morrón rojo" },
    { id: 15, nombre: "Limón" },
    { id: 16, nombre: "Naranja" },
    { id: 17, nombre: "Pera" },
    { id: 18, nombre: "Pepino" },
    { id: 19, nombre: "Lentejas" },
    { id: 20, nombre: "Porotos" },
    { id: 21, nombre: "Garbanzos" },
    { id: 22, nombre: "Arroz" },
    { id: 23, nombre: "Quinoa" },
    { id: 24, nombre: "Coco rallado" },
    { id: 25, nombre: "Canela" },
    { id: 26, nombre: "Jengibre" },
    { id: 27, nombre: "Almendras" },
    { id: 28, nombre: "Nueces" },
    { id: 29, nombre: "Zapallo" },
    { id: 30, nombre: "Calabaza" },
    { id: 31, nombre: "Palta" },
    { id: 32, nombre: "Durazno" },
    { id: 33, nombre: "Ciruela" },
    { id: 34, nombre: "Mandarina" },
    { id: 35, nombre: "Ananá" }
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
        <option value="">Selecciona un producto natural</option>
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
