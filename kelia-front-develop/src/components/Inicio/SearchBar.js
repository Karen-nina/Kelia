import React from 'react';

const SearchBar = ({ value, placeholder, onChange, onKeyDown }) => (
  <div className="mb-2 position-relative w-100">
    <input
      type="text"
      className="form-control w-100 shadow-sm"
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      onKeyDown={onKeyDown}
      style={{
        borderRadius: '12px',
        paddingLeft: '2.5rem',
        paddingTop: '8px',
        paddingBottom: '8px',
        fontSize: '1rem',
        border: '1px solid #CCD5AE',
        boxShadow: '0 3px 6px rgba(0,0,0,0.1)',
        transition: 'box-shadow 0.3s, border-color 0.3s',
      }}
      onFocus={(e) => {
        e.target.style.borderColor = '#9BB084';
        e.target.style.boxShadow = '0 0 0 3px rgba(155, 176, 132, 0.3)';
      }}
      onBlur={(e) => {
        e.target.style.borderColor = '#CCD5AE';
        e.target.style.boxShadow = '0 3px 6px rgba(0,0,0,0.1)';
      }}
    />
    <i
      className="bi bi-search position-absolute"
      style={{
        top: '50%',
        left: '14px',
        transform: 'translateY(-50%)',
        color: '#9BB084',
        fontSize: '1.2rem',
        pointerEvents: 'none',
      }}
    ></i>
  </div>
);


export default SearchBar;
