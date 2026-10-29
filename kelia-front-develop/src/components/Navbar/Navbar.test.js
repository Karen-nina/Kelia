import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import Navbar from "./Navbar";
import { MemoryRouter } from "react-router-dom";

const mockNavigate = jest.fn();
jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"),
  useNavigate: () => mockNavigate,
}));

const mockUserData = { avatar: "/img/mock.png" };
let mockUser = null;
let mockIsRegistered = false;
const mockLogout = jest.fn();

jest.mock("../../context/AuthContext", () => ({
  useAuth: () => ({
    user: mockUser,
    userData: mockUserData,
    isFullyRegistered: mockIsRegistered,
    logout: mockLogout,
  }),
}));

describe("Navbar", () => {
  beforeEach(() => {
    mockNavigate.mockClear();
    mockLogout.mockClear();
    mockUser = null;
    mockIsRegistered = false;
  });

  test("RendersLoginButtonWhenUserIsNotLoggedIn", () => {
    render(
      <MemoryRouter>
        <Navbar />
      </MemoryRouter>
    );
    const loginButtons = screen.getAllByText("Iniciar sesión");
    expect(loginButtons.length).toBeGreaterThan(0);
  });

  test("RendersLogoutButtonWhenUserIsLoggedIn", () => {
    mockUser = { uid: "123" };
    mockIsRegistered = true;
    render(
      <MemoryRouter>
        <Navbar />
      </MemoryRouter>
    );

    const logoutButtons = screen.getAllByText("Cerrar sesión");
    expect(logoutButtons.length).toBeGreaterThanOrEqual(1);
    expect(screen.getByAltText("Icono usuario")).toBeInTheDocument();
  });

  test("HandleLogoutCallsLogoutAndRemovesToken", async () => {
    mockUser = { uid: "123" };
    mockIsRegistered = true;
    render(
      <MemoryRouter>
        <Navbar />
      </MemoryRouter>
    );

    const removeItemSpy = jest.spyOn(Storage.prototype, "removeItem");
    mockLogout.mockResolvedValueOnce();

    const logoutButton = screen.getAllByText(/cerrar sesión/i)[0];
    fireEvent.click(logoutButton);

    await waitFor(() => expect(mockLogout).toHaveBeenCalled());
    await waitFor(() => expect(removeItemSpy).toHaveBeenCalledWith("token"));

    removeItemSpy.mockRestore();
  });

  test("ClickingLoginButtonCallsNavigate", () => {
    render(
      <MemoryRouter>
        <Navbar />
      </MemoryRouter>
    );

    const loginButton = screen.getAllByText("Iniciar sesión")[0];
    fireEvent.click(loginButton);

    expect(mockNavigate).toHaveBeenCalledWith("/login");
  });

  test("ToggleMenuChangesAriaExpanded", () => {
    render(
      <MemoryRouter>
        <Navbar />
      </MemoryRouter>
    );

    const toggler = screen.getByRole("button", { name: /toggle navigation/i });

    expect(toggler).toHaveAttribute("aria-expanded", "false");

    fireEvent.click(toggler);
    expect(toggler).toHaveAttribute("aria-expanded", "true");

    fireEvent.click(toggler);
    expect(toggler).toHaveAttribute("aria-expanded", "false");
  });
});