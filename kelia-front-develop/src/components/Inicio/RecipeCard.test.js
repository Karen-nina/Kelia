import React from 'react';
import { render, screen } from '@testing-library/react';
import RecipeCard from './RecipeCard';
import { BrowserRouter } from 'react-router-dom';

const renderWithRouter = (ui) => {
  return render(<BrowserRouter>{ui}</BrowserRouter>);
};

describe('RecipeCard Component', () => {
  const props = {
    id: '123',
    thumbnail: 'http://example.com/image.jpg',
    title: 'Delicious Recipe',
    tags: ['tag1', 'tag2', 'tag3'],
  };

  test('RendersCorrectlyWithGivenProps', () => {
    renderWithRouter(<RecipeCard {...props} />);

    const img = screen.getByAltText(props.title);
    expect(img).toBeInTheDocument();
    expect(img).toHaveAttribute('src', props.thumbnail);

    expect(screen.getByText(props.title)).toBeInTheDocument();

    expect(screen.getByText('tag1 | tag2 | tag3')).toBeInTheDocument();

    const link = screen.getByRole('link');
    expect(link).toHaveAttribute('href', `/receta/${props.id}`);
  });
});