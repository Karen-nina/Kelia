import React from 'react';
import Table from 'react-bootstrap/Table';
import './FiltroyTablas.css';

function TablaAnmat({ products }) {
  const productsUnified = products.reduce((acc, prod) => {
    const exists = acc.find(p =>
      p.salesName === prod.salesName &&
      p.brand === prod.brand &&
      p.productType === prod.productType
    );

    if (!exists) {
      acc.push({ ...prod });
    }

    return acc;
  }, []);

  return (
    <Table responsive="sm mx-4" bordered className="tabla-productos mt-3 w-100 text-start">
      <thead>
        <tr>
          <th><span className="titulo-nombre">Nombre</span></th>
          <th><span className="titulo-marca">Marca</span></th>
          <th><span className="titulo-tipo">Tipo</span></th>
        </tr>
      </thead>
      <tbody>
        {productsUnified.map((product) => (
          <tr key={product.id}>
            <td>{product.salesName}</td>
            <td>{product.brand}</td>
            <td>{product.productType}</td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
}

export default TablaAnmat;