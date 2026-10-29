import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import Search from "./Search";

describe("Search Component", () => {
  const mockItems = [
    { key: "1", title: "Apple" },
    { key: "2", title: "Banana" },
    { key: "3", title: "Orange" },
  ];

  test("RendersInitialEmptyState", () => {
    render(<Search items={mockItems} onChange={jest.fn()} />);
    const input = screen.getByPlaceholderText("Buscar marca...");
    expect(input.value).toBe("");
    expect(screen.queryByText(/Apple/i)).not.toBeInTheDocument();
  });

  test("FiltersItemsBasedOnSearchTerm", async () => {
    render(<Search items={mockItems} onChange={jest.fn()} />);
    const input = screen.getByPlaceholderText("Buscar marca...");

    fireEvent.change(input, { target: { value: "an" } });

    await waitFor(() => {
        const listItems = screen.getAllByRole("button");
        
        expect(
        listItems.some((item) => item.textContent.includes("Banana"))
        ).toBe(true);

        expect(
        listItems.some((item) => item.textContent.includes("Orange"))
        ).toBe(true);
    });
    });

  test("HighlightsMatchingTextInItems", async () => {
    render(<Search items={mockItems} onChange={jest.fn()} />);
    const input = screen.getByPlaceholderText("Buscar marca...");

    fireEvent.change(input, { target: { value: "an" } });

    const highlightedSpans = await screen.findAllByText((content, element) =>
      element.tagName === "SPAN" && element.style.backgroundColor === "yellow"
    );

    expect(highlightedSpans.length).toBeGreaterThanOrEqual(1);
  });

  test("CallsOnChangeWithSelectedItemsTitles", async () => {
    const onChangeMock = jest.fn();
    const { container } = render(<Search items={mockItems} onChange={onChangeMock} />);
    const input = screen.getByPlaceholderText("Buscar marca...");

    fireEvent.change(input, { target: { value: "a" } });

    const items = await new Promise((resolve) => {
      setTimeout(() => {
        const elems = container.querySelectorAll(".list-group-item");
        resolve(elems);
      }, 0);
    });

    fireEvent.click(items[0]);
    expect(onChangeMock).toHaveBeenCalledWith(["Apple"]);

    fireEvent.click(items[1]);
    expect(onChangeMock).toHaveBeenLastCalledWith(["Apple", "Banana"]);

    fireEvent.click(items[0]);
    expect(onChangeMock).toHaveBeenLastCalledWith(["Banana"]);
  });

  test("TogglesSelectionStateOnClick", async () => {
    const { container } = render(<Search items={mockItems} onChange={jest.fn()} />);
    const input = screen.getByPlaceholderText("Buscar marca...");

    fireEvent.change(input, { target: { value: "a" } });

    const items = await new Promise((resolve) => {
      setTimeout(() => {
        const elems = container.querySelectorAll(".list-group-item");
        resolve(elems);
      }, 0);
    });

    expect(items[0].classList.contains("active")).toBe(false);

    fireEvent.click(items[0]);
    expect(items[0].classList.contains("active")).toBe(true);

    fireEvent.click(items[0]);
    expect(items[0].classList.contains("active")).toBe(false);
  });
});