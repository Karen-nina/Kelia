import React from "react";
import { render } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import EditableStars from "./EditableStars";

describe("EditableStars", () => {
  test("RendersFiveStarsWithCorrectClasses", () => {
    const { container } = render(<EditableStars value={3} onChange={() => {}} />);

    const stars = container.querySelectorAll(".bi");
    expect(stars).toHaveLength(5);

    const filled = container.querySelectorAll(".bi-star-fill");
    const empty = container.querySelectorAll(".bi-star");

    expect(filled).toHaveLength(3);
    expect(empty).toHaveLength(2);
  });

  test("EachStarCallsOnChangeOnClick", async () => {
    const user = userEvent.setup();
    const mockOnChange = jest.fn();
    const { getByTestId } = render(<EditableStars value={0} onChange={mockOnChange} />);

    for (let i = 1; i <= 5; i++) {
      await user.click(getByTestId(`star-${i}`));
    }

    expect(mockOnChange).toHaveBeenCalledTimes(5);
    for (let i = 1; i <= 5; i++) {
      expect(mockOnChange).toHaveBeenNthCalledWith(i, i);
    }
  });
});