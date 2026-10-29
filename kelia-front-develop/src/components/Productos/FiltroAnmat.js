import React from 'react';
import FiltroMarca from './FiltroMarca';
import FiltroTipo from './FiltroTipo';

const FiltroAnmat = ({ brands = [], types = [], onChangeBrands, onChangeTypes }) => {
  return (
    <React.Fragment>
      <div className="mt-4">
        <h5 className="text-start mb-3"> Filtrar por</h5>
      </div>
      <div className="mt-4">
        <FiltroMarca brands={brands} onChange={onChangeBrands} />
      </div>
      <div className="mt-4">
        <FiltroTipo types={types} onChange={onChangeTypes} />
      </div>
    </React.Fragment>
  );
};

export default FiltroAnmat;