import React from "react";
import { render, screen } from "@testing-library/react";
import ProtectedRoute from "./ProtectedRoute";

jest.mock("../../context/AuthContext", () => ({
  useAuth: jest.fn(),
}));

const { useAuth } = require("../../context/AuthContext");

describe("ProtectedRoute", () => {
  it("ShowsSpinnerWhenLoadingIsTrue", () => {
    useAuth.mockReturnValue({ user: null, loading: true });
    render(
      <ProtectedRoute>
        <div>Contenido protegido</div>
      </ProtectedRoute>
    );
    expect(screen.getByText(/cargando/i)).toBeInTheDocument();
    expect(screen.queryByText("Contenido protegido")).not.toBeInTheDocument();
  });

  it("RendersChildrenWhenLoadingIsFalse", () => {
    useAuth.mockReturnValue({ user: { name: "Test" }, loading: false });
    render(
      <ProtectedRoute>
        <div>Contenido protegido</div>
      </ProtectedRoute>
    );
    expect(screen.getByText("Contenido protegido")).toBeInTheDocument();
    expect(screen.queryByRole("status")).not.toBeInTheDocument();
  });
});
