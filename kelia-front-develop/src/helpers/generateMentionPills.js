import React from 'react';
import { Link } from 'react-router-dom';

export function generateMentionPills(
  { recipes = [], products = [], places = [] },
  maxTotal = 3
) {
  const combined = [
    ...recipes.map((r) => ({ ...r, type: 'recipe', label: r.title })),
    ...products.map((p) => ({ ...p, type: 'product', label: p.title })),
    ...places.map((pl) => ({ ...pl, type: 'place', label: pl.name ?? pl.title })),
  ];

  if (combined.length === 0) return [];

  const ICON = { recipe: '🥣', product: '🛒', place: '📍' };
  //const COLOR = { recipe: 'primary', product: 'success', place: 'danger' };
  const PATH = { recipe: 'receta', product: 'productos', place: 'establecimiento'};

  const pills = combined.slice(0, maxTotal).map((item) => (
    <Link
      key={`${item.type}-${item.id}`}
      to={`/${PATH[item.type]}/${item.id}`}
      rel="noopener noreferrer"
      className={`pill text-decoration-none`}
      style={{ fontSize: '0.70rem' }}
    >
      <span className="me-1">{ICON[item.type]}</span>
      {item.label}
    </Link>
  ));

  const remaining = combined.length - maxTotal;
  if (remaining > 0) {
    pills.push(
      <span
        key="pill-more"
        className="badge bg-light text-secondary border"
        style={{ fontSize: '0.75rem' }}
      >
        +{remaining} más
      </span>
    );
  }

  return pills;
}