import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import Productos from './Productos';

jest.mock('../../components/Productos/FiltroAnmat', () => (props) => {
  return (
    <div>
      <button onClick={() => props.onChangeBrands(['BrandA'])}>Select BrandA</button>
      <button onClick={() => props.onChangeTypes(['Type1'])}>Select Type1</button>
    </div>
  );
});

jest.mock('../../components/Productos/TablaAnmat', () => (props) => {
  return (
    <div data-testid="tabla-anmat">
      {props.products.map(p => (
        <div key={p.id} data-testid="producto-item">{p.name}</div>
      ))}
    </div>
  );
});

jest.mock('../../components/Productos/BuscadorANMAT', () => (props) => {
  return (
    <button onClick={() => props.onBuscar('test search')}>Buscar</button>
  );
});

jest.mock('../../hooks/products', () => ({
  useGetProducts: jest.fn(),
}));

import { useGetProducts } from '../../hooks/products';

describe('Productos Component', () => {
  const mockData = {
    products: [
      { id: 1, name: 'Producto 1', brand: 'BrandA', productType: 'Type1' },
      { id: 2, name: 'Producto 2', brand: 'BrandB', productType: 'Type2' },
      { id: 3, name: 'Producto 3', brand: 'BrandA', productType: 'Type1' },
    ],
    "filter-brand": ['BrandA', 'BrandB'],
    "filter-type": ['Type1', 'Type2'],
  };

  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('ShowsLoadingStateInitially', () => {
    useGetProducts.mockReturnValue({ data: { products: [] }, loading: true, error: null });
    render(<Productos />);
    expect(screen.getByText(/Cargando productos/i)).toBeInTheDocument();
  });

  test('ShowsNoProductsMessageWhenEmpty', () => {
    useGetProducts.mockReturnValue({ data: { products: [] }, loading: false, error: null });
    render(<Productos />);
    expect(screen.getByText(/No se encontraron productos/i)).toBeInTheDocument();
  });

  test('RendersProductsAndPagination', async () => {
    useGetProducts.mockReturnValue({ data: mockData, loading: false, error: null });
    render(<Productos />);
    
    expect(await screen.findAllByTestId('producto-item')).toHaveLength(3);

    expect(screen.getByText((content, element) => {
      return content.startsWith('Página') && content.includes('de');
    })).toBeInTheDocument();

    expect(screen.getByText(/« Anterior/i)).toBeDisabled();

    expect(screen.getByText(/Siguiente »/i)).toBeDisabled();
  });

  test('FiltersProductsByBrandAndType', async () => {
    useGetProducts.mockReturnValue({ data: mockData, loading: false, error: null });
    render(<Productos />);

    fireEvent.click(screen.getByText('Select BrandA'));
    fireEvent.click(screen.getByText('Select Type1'));

    await waitFor(() => {
      const productos = screen.getAllByTestId('producto-item');
      expect(productos).toHaveLength(2);
      expect(productos[0]).toHaveTextContent('Producto 1');
      expect(productos[1]).toHaveTextContent('Producto 3');
    });
  });

  test('SearchUpdatesSearchTermsAndResetsFiltersAndPage', async () => {
    useGetProducts.mockReturnValue({ data: mockData, loading: false, error: null });
    render(<Productos />);

    fireEvent.click(screen.getByText('Buscar'));

    await waitFor(() => {
      expect(screen.getAllByTestId('producto-item').length).toBeGreaterThan(0);
    });

    expect(screen.getByText((content) => content.startsWith('Página'))).toBeInTheDocument();
  });

  test('PaginationButtonsChangePageCorrectly', async () => {
    const manyProducts = {
        products: Array.from({ length: 50 }, (_, i) => ({
        id: i,
        name: `Producto ${i + 1}`,
        brand: 'BrandA',
        productType: 'Type1',
        })),
        "filter-brand": ['BrandA'],
        "filter-type": ['Type1'],
    };

    useGetProducts.mockReturnValue({ data: manyProducts, loading: false, error: null });
    render(<Productos />);

    const paginaTexto = screen.getByText((content, element) =>
        element.classList.contains('texto-paginado')
    );

    expect(paginaTexto).toHaveTextContent('Página');
    expect(paginaTexto).toHaveTextContent('1');
    expect(paginaTexto).toHaveTextContent('3');

    expect(screen.getByText(/« Anterior/i)).toBeDisabled();

    fireEvent.click(screen.getByText(/Siguiente »/i));

    await waitFor(() => {
        const paginaTexto2 = screen.getByText((content, element) =>
        element.classList.contains('texto-paginado')
        );
        expect(paginaTexto2).toHaveTextContent('Página');
        expect(paginaTexto2).toHaveTextContent('2');
        expect(paginaTexto2).toHaveTextContent('3');
    });

    expect(screen.getByText(/« Anterior/i)).toBeEnabled();

    fireEvent.click(screen.getByText(/« Anterior/i));

    await waitFor(() => {
        const paginaTexto1 = screen.getByText((content, element) =>
        element.classList.contains('texto-paginado')
        );
        expect(paginaTexto1).toHaveTextContent('Página');
        expect(paginaTexto1).toHaveTextContent('1');
        expect(paginaTexto1).toHaveTextContent('3');
    });
    });
});