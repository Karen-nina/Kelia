import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import PlacesMap from './PlacesMap';

jest.mock('@react-google-maps/api', () => {
  const React = require('react');

  const MockGoogleMap = ({ children, mapContainerStyle, center, zoom, onLoad }) => {
    React.useEffect(() => {
      onLoad && onLoad({
        panTo: jest.fn(),
        setZoom: jest.fn(),
      });
    }, [onLoad]);
    return (
      <div
        data-testid="google-map"
        style={mapContainerStyle}
        data-center={JSON.stringify(center)}
        data-zoom={zoom}
      >
        {children}
      </div>
    );
  };

  const MockMarker = ({ position, icon, onClick }) => (
    <div
      data-testid="marker"
      data-position={JSON.stringify(position)}
      data-icon-url={icon.url}
      onClick={onClick}
    />
  );

  return {
    GoogleMap: MockGoogleMap,
    Marker: MockMarker,
  };
});

describe('PlacesMap', () => {
  const lugaresMock = [
    { lat: 10, lng: 20, category: 'Cafeteria', name: 'Place1' },
    { lat: 30, lng: 40, category: 'Restaurante', name: 'Place2' },
    { lat: 50, lng: 60, category: 'UnknownCategory', name: 'Place3' },
  ];

  test('RendersMapWithCorrectContainerStyleAndCenter', () => {
    render(<PlacesMap lugares={[]} onSelectPlace={() => {}} selectedPlace={null} />);
    const map = screen.getByTestId('google-map');
    expect(map).toHaveStyle({ width: '100%', height: '80vh' });
    expect(map.getAttribute('data-center')).toContain('-34.6037');
    expect(map.getAttribute('data-center')).toContain('-58.3816');
  });

  test('RendersMarkersWithCorrectIcons', () => {
    render(<PlacesMap lugares={lugaresMock} onSelectPlace={() => {}} selectedPlace={null} />);
    const markers = screen.getAllByTestId('marker');
    expect(markers).toHaveLength(3);

    expect(markers[0].getAttribute('data-icon-url')).toBe('/img/markers/cafeteria.png');
    expect(markers[1].getAttribute('data-icon-url')).toBe('/img/markers/restaurante.png');
    expect(markers[2].getAttribute('data-icon-url')).toBe('/img/markers/default.png');
  });

  test('CallsOnSelectPlaceWhenMarkerClicked', () => {
    const onSelectPlaceMock = jest.fn();
    render(<PlacesMap lugares={lugaresMock} onSelectPlace={onSelectPlaceMock} selectedPlace={null} />);
    const markers = screen.getAllByTestId('marker');
    fireEvent.click(markers[1]);
    expect(onSelectPlaceMock).toHaveBeenCalledWith(lugaresMock[1]);
  });
});
