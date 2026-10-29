import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import DetalleReceta from './DetalleReceta';
import { MemoryRouter, Route, Routes } from 'react-router-dom';

jest.mock('../../firebase');
jest.mock('../../context/AuthContext');
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useParams: () => ({ id: '123' }),
  useNavigate: () => jest.fn(),
}));

global.fetch = jest.fn(() =>
  Promise.resolve({
    ok: true,
    json: () =>
      Promise.resolve({
        id: '123',
        title: 'Torta de Chocolate',
        thumbnail: '/torta.jpg',
        user: {
          avatar: '/img/chef.jpg',
          displayName: 'Chef',
          username: 'chefuser',
        },
        rating: 4,
        tiempo: 'Preparación rápida',
        preparationTime: 45,
        save_count: 12,
        ingredients: ['1 taza de harina', '2 huevos'],
        instructions: ['Mezclar los ingredientes', 'Hornear 30 minutos'],
        tags: ['postre', 'chocolate'],
        reviews: [
          {
            user: {
              avatar: '/img/avatar.png',
              displayName: 'Tester',
            },
            comment: 'Muy buena!',
            rating: 5,
            timeAgo: 'hace 2 días',
          },
        ],
      }),
  })
);

describe('DetalleReceta Component', () => {
  beforeEach(() => {
    fetch.mockClear();
  });

  test('RendersRecipeDetailsOnSuccessfulFetch', async () => {
    render(
      <MemoryRouter initialEntries={['/receta/123']}>
        <Routes>
          <Route path="/receta/:id" element={<DetalleReceta />} />
        </Routes>
      </MemoryRouter>
    );

    await waitFor(() => {
      expect(screen.getByRole('heading', { name: /Torta de Chocolate/i })).toBeInTheDocument();

      expect(screen.getByText(/1 taza de harina/i)).toBeInTheDocument();
      expect(screen.getByText(/2 huevos/i)).toBeInTheDocument();

      expect(screen.getByText(/Mezclar los ingredientes/i)).toBeInTheDocument();
      expect(screen.getByText(/Hornear 30 minutos/i)).toBeInTheDocument();

      expect(screen.getByText('#postre')).toBeInTheDocument();
      expect(screen.getByText('#chocolate')).toBeInTheDocument();

      const authorElements = screen.getAllByText((_, element) =>
        element.textContent.includes('Por') &&
        element.textContent.includes('(@chefuser)')
      );
      expect(authorElements.length).toBeGreaterThan(0);

      expect(screen.getByText('Muy buena!')).toBeInTheDocument();
      expect(screen.getByText(/hace 2 días/i)).toBeInTheDocument();
    });
  });
});