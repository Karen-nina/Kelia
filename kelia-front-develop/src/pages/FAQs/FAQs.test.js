import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import FAQs from "./FAQs";
import '@testing-library/jest-dom';

describe("FAQsComponent", () => {
  test("FirstAnswerIsVisibleByDefault", () => {
    render(<FAQs />);
    const firstQuestion = screen.getByText(/¿Qué es la celiaquía\?/i);

    expect(firstQuestion.closest('button')).toHaveAttribute('aria-expanded', 'true');
  });

  test("ClickingQuestionTogglesAccordionItems", () => {
    render(<FAQs />);

    const firstQuestion = screen.getByText(/¿Qué es la celiaquía\?/i);
    const secondQuestion = screen.getByText(/¿Cómo sé si un producto es apto para celíacos\?/i);

    expect(firstQuestion.closest('button')).toHaveAttribute('aria-expanded', 'true');
    expect(secondQuestion.closest('button')).toHaveAttribute('aria-expanded', 'false');

    fireEvent.click(secondQuestion);

    expect(secondQuestion.closest('button')).toHaveAttribute('aria-expanded', 'true');
    expect(firstQuestion.closest('button')).toHaveAttribute('aria-expanded', 'false');
  });
});