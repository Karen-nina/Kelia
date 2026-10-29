import React from "react";
import { render, screen } from "@testing-library/react";
import ProtectedLayout from "./ProtectedLayout";
import ProtectedRoute from "../ProtectedRoute/ProtectedRoute";

jest.mock("react-router-dom", () => ({
  Outlet: () => <div data-testid="outlet-mock" />,
}));

jest.mock("../ProtectedRoute/ProtectedRoute", () => ({ children }) => (
  <div data-testid="protected-route-mock">{children}</div>
));

describe("ProtectedLayout", () => {
  test("RendersProtectedRouteWithOutlet", () => {
    render(<ProtectedLayout />);

    const protectedRoute = screen.getByTestId("protected-route-mock");
    expect(protectedRoute).toBeInTheDocument();

    expect(screen.getByTestId("outlet-mock")).toBeInTheDocument();
  });
});