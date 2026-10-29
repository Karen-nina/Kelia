import React from 'react'
import AgregadoRecetas from '../../components/Recetas/AgregadoRecetas'

function NuevaReceta() {
  return (
    <div className="receta-nueva-container">
      <div className="nueva-container">
        <div><h2 className="seccion-titulo text-center">Nueva receta</h2></div>
      </div>
      <AgregadoRecetas></AgregadoRecetas>
    </div>
  )
}

export default NuevaReceta