import React from "react";

const EditableStars = ({ value, onChange }) => {
  return (
    <div>
      {[1, 2, 3, 4, 5].map((star) => (
        <i
          key={star}
          className={`bi ${value >= star ? "bi-star-fill" : "bi-star"} text-warning me-1`}
          style={{ cursor: "pointer", fontSize: "1.3em" }}
          onClick={() => onChange(star)}
          data-testid={`star-${star}`}
        />
      ))}
    </div>
  );
};

export default EditableStars;