import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import PlaceDetail from './PlaceDetail';

describe('PlaceDetail', () => {
  const lugarMock = {
    name: 'Test Place',
    category: 'Cafeteria',
  };

  const apiDataMock = {
    rating: 4.5,
    totalUserRatings: 20,
  };

  test('ReturnsNullWhenNoLugar', () => {
    const { container } = render(<PlaceDetail lugar={null} />);
    expect(container.firstChild).toBeNull();
  });

  test('RendersLugarNameAndCategoryWithIcon', () => {
    render(<PlaceDetail lugar={lugarMock} apiError={false} apiData={null} onClose={() => {}} />);
    expect(screen.getByText('Test Place')).toBeInTheDocument();
    expect(screen.getByText('Cafeteria')).toBeInTheDocument();
    const icon = screen.getByAltText('Cafeteria');
    expect(icon).toHaveAttribute('src', '/img/markers/cafeteria.png');
  });

  test('CallsOnCloseWhenCloseButtonClicked', () => {
    const onCloseMock = jest.fn();
    render(<PlaceDetail lugar={lugarMock} apiError={false} apiData={null} onClose={onCloseMock} />);
    const closeButton = screen.getByRole('button', { name: /cerrar/i });
    fireEvent.click(closeButton);
    expect(onCloseMock).toHaveBeenCalledTimes(1);
  });

  test('RendersKeliaRatingSection', () => {
    render(<PlaceDetail lugar={lugarMock} apiError={false} apiData={null} onClose={() => {}} />);
    expect(screen.getByText(/valorado por/i)).toBeInTheDocument();
    expect(screen.getByAltText('logo-kelia')).toBeInTheDocument();
  });

  test('RendersApiDataWhenNoError', () => {
    render(<PlaceDetail lugar={lugarMock} apiError={false} apiData={apiDataMock} onClose={() => {}} />);
    expect(screen.getByText(`(${apiDataMock.rating})`)).toBeInTheDocument();
    expect(screen.getByText(`Total de reseñas: ${apiDataMock.totalUserRatings}`)).toBeInTheDocument();
    expect(screen.getByAltText('logo-google-maps')).toBeInTheDocument();
  });

  test('DoesNotRenderApiDataWhenApiError', () => {
    render(<PlaceDetail lugar={lugarMock} apiError={true} apiData={apiDataMock} onClose={() => {}} />);
    expect(screen.queryByAltText('logo-google-maps')).not.toBeInTheDocument();
    expect(screen.queryByText(`(${apiDataMock.rating})`)).not.toBeInTheDocument();
  });
});