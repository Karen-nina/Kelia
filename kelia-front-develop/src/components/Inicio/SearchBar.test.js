import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import SearchBar from './SearchBar';

describe('SearchBar', () => {
  test('RendersWithPlaceholder', () => {
    render(<SearchBar placeholder="Buscar algo..." />);
    const input = screen.getByPlaceholderText('Buscar algo...');
    expect(input).toBeInTheDocument();
  });

  test('CallsOnChangeWhenTyping', () => {
    const handleChange = jest.fn();
    render(<SearchBar placeholder="Buscar..." onChange={handleChange} />);
    const input = screen.getByPlaceholderText('Buscar...');

    fireEvent.change(input, { target: { value: 'hola' } });
    expect(handleChange).toHaveBeenCalledTimes(1);
  });

  test('CallsOnKeyDownWhenPressingKey', () => {
    const handleKeyDown = jest.fn();
    render(<SearchBar placeholder="Buscar..." onKeyDown={handleKeyDown} />);
    const input = screen.getByPlaceholderText('Buscar...');

    fireEvent.keyDown(input, { key: 'Enter', code: 'Enter' });
    expect(handleKeyDown).toHaveBeenCalledTimes(1);
  });

  test('KeepsControlledValue', () => {
    const value = 'texto inicial';
    render(<SearchBar placeholder="Buscar..." value={value} onChange={() => {}} />);
    const input = screen.getByPlaceholderText('Buscar...');
    expect(input.value).toBe(value);
  });

  test('ChangesStyleOnFocus', () => {
    render(<SearchBar placeholder="Buscar..." />);
    const input = screen.getByPlaceholderText('Buscar...');

    fireEvent.focus(input);
    const computedStyle = window.getComputedStyle(input);

    expect(computedStyle.borderColor).toBe('#9bb084');
    expect(computedStyle.boxShadow).toContain('rgba(155, 176, 132, 0.3)');
  });

  test('RestoresStyleOnBlur', () => {
    render(<SearchBar placeholder="Buscar..." />);
    const input = screen.getByPlaceholderText('Buscar...');

    fireEvent.focus(input);
    fireEvent.blur(input);
    const computedStyle = window.getComputedStyle(input);

    expect(computedStyle.borderColor).toBe('#ccd5ae');
    expect(computedStyle.boxShadow).toContain('rgba(0,0,0,0.1)');
  });
});