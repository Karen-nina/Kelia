import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import FiltroMarca from './FiltroMarca';

describe('FiltroMarca', () => {
  const brandsMock = Array.from({ length: 15 }, (_, i) => `Brand ${i + 1}`);

  test('RendersSpinnerWhenNoBrands', () => {
    render(<FiltroMarca brands={[]} />);
    expect(screen.getByText(/cargando/i)).toBeInTheDocument();
  });

  test('RendersOnlyTenCheckboxesInitially', () => {
    render(<FiltroMarca brands={brandsMock} />);
    const checkboxes = screen.getAllByRole('checkbox');
    expect(checkboxes).toHaveLength(10);
  });

  test('ShowsAllCheckboxesAfterClickingViewMore', () => {
    render(<FiltroMarca brands={brandsMock} />);
    fireEvent.click(screen.getByText('Ver más'));
    const checkboxes = screen.getAllByRole('checkbox');
    expect(checkboxes).toHaveLength(brandsMock.length);
    expect(screen.getByText('Ver menos')).toBeInTheDocument();
  });

  test('TogglesCheckboxSelectionAndCallsOnChange', () => {
    const onChangeMock = jest.fn();
    render(<FiltroMarca brands={brandsMock} onChange={onChangeMock} />);
    const firstCheckbox = screen.getAllByRole('checkbox')[0];

    fireEvent.click(firstCheckbox);
    expect(onChangeMock).toHaveBeenCalledWith([brandsMock[0]]);
    expect(firstCheckbox).toBeChecked();

    fireEvent.click(firstCheckbox);
    expect(onChangeMock).toHaveBeenCalledWith([]);
    expect(firstCheckbox).not.toBeChecked();
  });

  test('TogglesViewMoreAndViewLessButton', () => {
    render(<FiltroMarca brands={brandsMock} />);
    const button = screen.getByRole('button', { name: 'Ver más' });

    fireEvent.click(button);
    expect(button.textContent).toBe('Ver menos');

    fireEvent.click(button);
    expect(button.textContent).toBe('Ver más');
  });
});