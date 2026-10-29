import React, { useEffect, useRef } from 'react';
import { GoogleMap, Marker } from '@react-google-maps/api';

const containerStyle = {
  width: '100%',
  height: '80vh',
};

const defaultCenter = {
  lat: -34.6037,
  lng: -58.3816,
};

const iconosPorCategoria = {
  Cafeteria: '/img/markers/cafeteria.png',
  Restaurante: '/img/markers/restaurante.png',
  Delivery: '/img/markers/delivery.png',
  Hamburgueseria: '/img/markers/hamburgueseria.png',
  Cerveceria: '/img/markers/cerveceria.png',
  Tienda: '/img/markers/tienda.png',
  Supermercado: '/img/markers/supermercado.png',
  Heladeria: '/img/markers/heladeria.png',
  Pizzeria: '/img/markers/pizzeria.png',
  Panaderia: '/img/markers/panaderia.png',
  default: '/img/markers/default.png',
};

  function PlacesMap({ lugares, onSelectPlace, selectedPlace }) {
  const mapRef = useRef(null);

  const obtenerIcono = (categoria) => ({
    url: iconosPorCategoria[categoria] || iconosPorCategoria.default,
    scaledSize: { width: 30, height: 30 },
  });

  const onLoad = (mapInstance) => {
    mapRef.current = mapInstance;
  };

  useEffect(() => {
    if (selectedPlace && mapRef.current) {
      mapRef.current.panTo({
        lat: selectedPlace.lat,
        lng: selectedPlace.lng,
      });
      mapRef.current.setZoom(10);
    }
  }, [selectedPlace]);

  return (
    <GoogleMap
      mapContainerStyle={containerStyle}
      center={defaultCenter}
      zoom={10}
      onLoad={onLoad}
    >
      {lugares.map((lugar, idx) => (
        <Marker
          key={idx}
          position={{ lat: lugar.lat, lng: lugar.lng }}
          icon={obtenerIcono(lugar.category)}
          onClick={() => onSelectPlace(lugar)}
        />
      ))}
    </GoogleMap>
  );
}

export default React.memo(PlacesMap);