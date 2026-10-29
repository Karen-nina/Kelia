import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import FiltroTipo from './FiltroTipo';

describe('FiltroTipo', () => {
  const typesMock = Array.from({ length: 15 }, (_, i) => `Type ${i + 1}`);

  test('RendersSpinnerWhenNoTypes', () => {
    render(<FiltroTipo types={[]} />);
    expect(screen.getByText(/cargando/i)).toBeInTheDocument();
  });

  test('RendersOnlyTenCheckboxesInitially', () => {
    render(<FiltroTipo types={typesMock} />);
    const checkboxes = screen.getAllByRole('checkbox');
    expect(checkboxes).toHaveLength(10);
  });

  test('ShowsAllCheckboxesAfterClickingViewMore', () => {
    render(<FiltroTipo types={typesMock} />);
    fireEvent.click(screen.getByText('Ver más'));
    const checkboxes = screen.getAllByRole('checkbox');
    expect(checkboxes).toHaveLength(typesMock.length);
    expect(screen.getByText('Ver menos')).toBeInTheDocument();
  });

  test('TogglesCheckboxSelectionAndCallsOnChange', () => {
    const onChangeMock = jest.fn();
    render(<FiltroTipo types={typesMock} onChange={onChangeMock} />);

    const firstCheckbox = screen.getAllByRole('checkbox')[0];
    fireEvent.click(firstCheckbox);
    expect(onChangeMock).toHaveBeenCalledWith([typesMock[0]]);
    expect(firstCheckbox).toBeChecked();

    fireEvent.click(firstCheckbox);
    expect(onChangeMock).toHaveBeenCalledWith([]);
    expect(firstCheckbox).not.toBeChecked();
  });

  test('TogglesViewMoreAndViewLessButton', () => {
    render(<FiltroTipo types={typesMock} />);
    const button = screen.getByRole('button', { name: 'Ver más' });

    fireEvent.click(button);
    expect(button.textContent).toBe('Ver menos');

    fireEvent.click(button);
    expect(button.textContent).toBe('Ver más');
  });
});