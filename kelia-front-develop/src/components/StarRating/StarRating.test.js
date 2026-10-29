import React from "react";
import { render } from "@testing-library/react";
import StarRating from "./StarRating";

describe("StarRating", () => {
  test("RendersFiveEmptyStarsWhenRatingIsZero", () => {
    const { container } = render(<StarRating rating={0} />);
    expect(container.querySelectorAll(".bi-star")).toHaveLength(5);
    expect(container.querySelectorAll(".bi-star-fill")).toHaveLength(0);
    expect(container.querySelectorAll(".bi-star-half")).toHaveLength(0);
  });

  test("RendersFiveFilledStarsWhenRatingIsFive", () => {
    const { container } = render(<StarRating rating={5} />);
    expect(container.querySelectorAll(".bi-star-fill")).toHaveLength(5);
    expect(container.querySelectorAll(".bi-star")).toHaveLength(0);
    expect(container.querySelectorAll(".bi-star-half")).toHaveLength(0);
  });

  test("RendersTwoFilledOneHalfTwoEmptyStarsWhenRatingIsTwoPointFive", () => {
    const { container } = render(<StarRating rating={2.5} />);
    expect(container.querySelectorAll(".bi-star-fill")).toHaveLength(2);
    expect(container.querySelectorAll(".bi-star-half")).toHaveLength(1);
    expect(container.querySelectorAll(".bi-star")).toHaveLength(2);
  });

  test("RendersThreeFilledTwoEmptyStarsWhenRatingIsThree", () => {
    const { container } = render(<StarRating rating={3} />);
    expect(container.querySelectorAll(".bi-star-fill")).toHaveLength(3);
    expect(container.querySelectorAll(".bi-star")).toHaveLength(2);
    expect(container.querySelectorAll(".bi-star-half")).toHaveLength(0);
  });

  test("RendersThreeFilledOneHalfOneEmptyStarsWhenRatingIsThreePoint5", () => {
    const { container } = render(<StarRating rating={3.5} />);
    expect(container.querySelectorAll(".bi-star-fill")).toHaveLength(3);
    expect(container.querySelectorAll(".bi-star-half")).toHaveLength(1);
    expect(container.querySelectorAll(".bi-star")).toHaveLength(1);
  });

  test("RendersDefaultZeroRatingWhenNoPropIsPassed", () => {
    const { container } = render(<StarRating />);
    expect(container.querySelectorAll(".bi-star")).toHaveLength(5);
    expect(container.querySelectorAll(".bi-star-fill")).toHaveLength(0);
    expect(container.querySelectorAll(".bi-star-half")).toHaveLength(0);
  });
});