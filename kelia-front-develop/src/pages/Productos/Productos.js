import React from 'react';
import { Col, Container, Row, Spinner, Card, Button } from "react-bootstrap";
import TablaAnmat from "../../components/Productos/TablaAnmat";
import FiltroAnmat from "../../components/Productos/FiltroAnmat";
import { useEffect, useState } from "react";
import { useGetProducts } from "../../hooks/products";
import BuscadorANMAT from "../../components/Productos/BuscadorANMAT";
import './Productos.css';

const Productos = () => {
  const [searchTerms, setSearchTerms] = useState("");
  const [brands, setBrands] = useState([]);
  const [types, setTypes] = useState([]);

  const [selectedBrands, setSelectedBrands] = useState([]);
  const [selectedTypes, setSelectedTypes] = useState([]);

  const [page, setPage] = useState(1);
  const itemsPerPage = 20;

  const { data, error, loading } = useGetProducts(searchTerms);

  useEffect(() => {
    if (data["filter-brand"]) {
      setBrands(data["filter-brand"]);
    }
    if (data["filter-type"]) {
      setTypes(data["filter-type"]);
    }
  }, [data]);

  useEffect(() => {
    setSelectedBrands([]);
    setSelectedTypes([]);
    setPage(1);
  }, [searchTerms]);

  const filteredProducts = data.products.filter(product =>
    (selectedBrands.length === 0 || selectedBrands.includes(product.brand)) &&
    (selectedTypes.length === 0 || selectedTypes.includes(product.productType))
  );

  const totalPages = Math.ceil(filteredProducts.length / itemsPerPage);
  const startIndex = (page - 1) * itemsPerPage;
  const currentProducts = filteredProducts.slice(startIndex, startIndex + itemsPerPage);

  const handlePrevPage = () => {
    setPage((prev) => Math.max(prev - 1, 1));
  };

  const handleNextPage = () => {
    setPage((prev) => Math.min(prev + 1, totalPages));
  };

  return (
    <div className="container">
      <div className="productos-container">
        <h3 className="producto-titulo fw-bolder">Listado de productos sin TACC de la ANMAT</h3>
        <h4 className="producto-subtitulo mb-2">
          Buscá y encontrá diversas opciones para tu alimentación
        </h4>
        <Container fluid>
          <Row className="producto-filtro">
            <Col md={3}>
              <FiltroAnmat
                brands={brands}
                types={types}
                onChangeBrands={setSelectedBrands}
                onChangeTypes={setSelectedTypes}
              />
            </Col>
            <Col md={9}>
              <Card className="producto-card p-2 mt-3 shadow-sm">
                <BuscadorANMAT onBuscar={(term) => setSearchTerms(term)} />
                {loading ? (
                  <div className="d-flex flex-column justify-content-center align-items-center" style={{ height: '200px' }}>
                        <p className="carga-productos mb-3">Cargando productos...</p>
                        <Spinner animation="border" role="status">
                          <span className="visually-hidden">Cargando...</span>
                        </Spinner>
                      </div>
                ) : currentProducts.length === 0 ? (
                  <p>No se encontraron productos para los filtros seleccionados.</p>
                ) : (
                  <>
                    <TablaAnmat products={currentProducts} />
                    <div className="paginacion-container">
                      <div>
                        <Button className="button-paginado" onClick={handlePrevPage} disabled={page === 1}>
                          &laquo; Anterior
                        </Button>
                      </div>
                      <div className="texto-paginado">
                        Página <strong>{page}</strong> de <strong>{totalPages}</strong>
                      </div>
                      <div>
                        <Button className="button-paginado" onClick={handleNextPage} disabled={page === totalPages || totalPages === 0}>
                          Siguiente &raquo;
                        </Button>
                      </div>
                    </div>
                  </>
                )}
              </Card>
            </Col>
          </Row>
        </Container>
      </div>
    </div>
  );
};

export default Productos;