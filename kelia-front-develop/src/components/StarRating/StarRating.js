import React from 'react';
import 'bootstrap-icons/font/bootstrap-icons.css';

const StarRating = ({ rating = 0 }) => {
  const stars = [];
  for (let i = 1; i <= 5; i++) {
    if (rating >= i) {
      stars.push(<i key={i} className="bi bi-star-fill text-warning me-1" />);
    } else if (rating >= i - 0.5) {
      stars.push(<i key={i} className="bi bi-star-half text-warning me-1" />);
    } else {
      stars.push(<i key={i} className="bi bi-star text-warning me-1" />);
    }
  }
  return <>{stars}</>;
};

export default StarRating;