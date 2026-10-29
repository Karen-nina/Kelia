import React, { useState } from "react";
import SelectTipoProducto from "../../components/Recetas/SelectTipoProducto";
import SelectProducto from "../../components/Recetas/SelectProducto";
import SelectUnit from "../../components/Recetas/SelectUnit";
import SelectUnitNatural from "../../components/Recetas/SelectUnitNatural";
import SelectCantidad from "../../components/Recetas/SelectCantidad";
import SelectProductNatural from "../../components/Recetas/SelectProductNatural";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

const inicial = [
  {
    key: new Date().getTime(),
    typeId: 0,
    productId: 0,
    unidId: 0,
    quantity: 0,
  },
];

const inicialNatural = [
  {
    key: new Date().getTime(),
    productId: 0,
    unidId: 0,
    quantity: 0,
  },
];

const AgregadoRecetas = () => {
  const [rows, setRows] = useState(inicial); // usamos índices como clave
  const [rowsNatural, setRowsNatural] = useState(inicialNatural); // usamos índices como clave
  const [imagen, setImagen] = useState(null);

  const addRow = () => {
    setRows((prev) => [
      ...prev,
      { key: new Date().getTime(), typeId: 0, productId: 0 },
    ]);
  };

  const addRowNatural = () => {
    setRowsNatural((prev) => [
      ...prev,
      { key: new Date().getTime(), typeId: 0, productId: 0 },
    ]);
  };

  const handleDelete = (key) => {
    const result = rows.filter((row, i) => row.key !== key);
    setRows(result);
  };

  const handleDeleteNatural = (key) => {
    const result = rowsNatural.filter((row, i) => row.key !== key);
    setRowsNatural(result);
  };

  const handleChangeProduct = (index, product) => {
    rows[index].productId = product.target.value;
    setRows([...rows]);
  };

  const handleChangeTypeProduct = (index, product) => {
    rows[index].typeId = product.target.value;
    setRows([...rows]);
  };

  const handleImagenChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setImagen(URL.createObjectURL(file));
    }
  };

  return (
    <div className="todo-agregado">
      <div className="agregado-container">
        <h4 className="seccion-subtitulo mt-4">Titulo receta</h4>
        <div className="nombre-receta">
          <textarea
            className="form-control "
            id="textoLibre"
            rows="1"
            placeholder="Escriba acá el nombre de su receta..."
          ></textarea>
        </div>
      </div>
      <div className="añadir-container p-4">
        <button
          className="añadir-button"
          onClick={() => document.getElementById("input-imagen").click()}
        >
          Añadir imagen
        </button>
        <input
          type="file"
          id="input-imagen"
          accept="image/*"
          onChange={handleImagenChange}
          style={{ display: "none" }}
        />
        {imagen ? (
          <img
            src={imagen}
            alt="Vista previa"
            style={{
              maxHeight: "100%",
              maxWidth: "100%",
              objectFit: "contain",
            }}
          />
        ) : (
          <div
            className="border rounded bg-light d-flex justify-content-center align-items-center"
            style={{ height: "200px" }}
          >
            <div
              className="text-center text-muted"
              onClick={() => document.getElementById("input-imagen").click()}
            >
              <i
                className="bi bi-camera-fill"
                style={{ fontSize: "2rem", cursor: "pointer" }}
              ></i>
              <div
                style={{ color: "#000", fontWeight: "500", cursor: "pointer" }}
              >
                Subir foto
              </div>
            </div>
          </div>
        )}
      </div>
      <div className="agregado-container">
        <h4 className="seccion-subtitulo mt-4">Ingredientes</h4>
        <h5 className="seccion-parrafo mt-2">
          Agregá productos de la lista de ANMAT a tu receta
        </h5>
      </div>
      <div className="select-container w-100">
        <div className="selector w-100">
          {rows.map((row, index) => (
            <div className="select-row" key={row.key}>
              <Row>
                <Col xs={12} md={11}>
                  <Row>
                    <Col xs={12} md={6}>
                      <SelectTipoProducto
                        handleChange={(type) =>
                          handleChangeTypeProduct(index, type)
                        }
                      />
                    </Col>
                    <Col xs={12} md={6}>
                      <SelectProducto
                        typeId={[row.typeId]}
                        handleChange={(product) =>
                          handleChangeProduct(index, product)
                        }
                      />
                    </Col>
                    <Col xs={12} md={6}>
                      <SelectUnit />
                    </Col>
                    <Col xs={12} md={6}>
                      <SelectCantidad />
                    </Col>
                  </Row>
                </Col>
                <Col
                  xs={12}
                  md={1}
                  style={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                  }}
                >
                  {rows.length > 1 && (
                    <i
                      class="tacho bi bi-trash fs-4"
                      onClick={() => handleDelete(row.key)}
                    ></i>
                  )}
                </Col>
              </Row>
            </div>
          ))}
        </div>
      </div>

      <div className="container-agregado mt-3">
        <button className="agregado-linea-receta-button" onClick={addRow}>
          Agregar una linea
        </button>
      </div>
      <div className="select-container-natural w-100">
        <div className="selector w-100">
          <div className="agregado-container">
            <h5 className="seccion-parrafo mt-2">
              Agregá productos naturales a tu receta
            </h5>
          </div>

          {rowsNatural.map((row, index) => (
            <div className="select-row" key={row.key}>
              <Row>
                <Col xs={12} md={11}>
                  <Row>
                    <Col xs={12} md={6}>
                      <SelectProductNatural />
                    </Col>
                    <Col xs={12} md={6}>
                      <SelectUnitNatural />
                    </Col>
                    <Col xs={12} md={6}>
                      <SelectCantidad />
                    </Col>
                  </Row>
                </Col>

                <Col
                  xs={12}
                  md={1}
                  style={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                  }}
                >
                  {rowsNatural.length > 1 && (
                    <i
                      class="tacho bi bi-trash fs-4"
                      onClick={() => handleDeleteNatural(row.key)}
                    ></i>
                  )}
                </Col>
              </Row>
            </div>
          ))}
        </div>
      </div>
      <div className="container-agregado mt-3">
        <button
          className="agregado-linea-receta-button"
          onClick={addRowNatural}
        >
          Agregar una linea
        </button>
      </div>

      <div className="agregado-container">
        <h4 className="seccion-subtitulo mt-3 mb-2">Instrucciones</h4>
        <div className="instrucciones-receta">
          <textarea
            className="form-control"
            id="textoLibre"
            rows="4"
            placeholder="Escriba acá las instrucciones de su receta..."
          ></textarea>
        </div>
      </div>

      <div className="agregado-container">
        <h4 className="seccion-subtitulo mt-5 mb-2">Tiempo</h4>
        <div className="instrucciones-receta">
          {/* Tiempo de Preparación */}
          <Row>
            <Col xs={12} md={6}>
              <label className="form-label seccion-tiempos">Preparación</label>
              <div className="input-group">
                <input
                  type="number"
                  className="form-control"
                  placeholder="Tiempo de preparación"
                  min="0"
                />
                <span className="input-group-text">minutos</span>
                <span className="input-group-text">
                  <i className="bi bi-clock"></i> {/* Bootstrap Icons */}
                </span>
              </div>
            </Col>

            {/* Tiempo de Cocción */}
            <Col xs={12} md={6}>
              <label className="form-label seccion-tiempos">Cocción</label>
              <div className="input-group">
                <input
                  type="number"
                  className="form-control"
                  placeholder="Tiempo de cocción"
                  min="0"
                />

                <span className="input-group-text">minutos</span>
                <span className="input-group-text">
                  <i className="bi bi-clock"></i>
                </span>
              </div>
            </Col>
          </Row>
        </div>
      </div>

      <div className="container-guardado mt-3">
        <button className="guardado-receta-button mb-5">Guardar receta</button>
      </div>
    </div>
  );
};

export default AgregadoRecetas;
