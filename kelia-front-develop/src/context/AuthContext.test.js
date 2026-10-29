import React from 'react';
import { render, waitFor, act } from '@testing-library/react';
import { AuthProvider, useAuth } from './AuthContext';
import { onAuthStateChanged, signOut } from 'firebase/auth';

jest.mock('../firebase');

jest.mock('firebase/auth', () => {
  const actual = jest.requireActual('firebase/auth');
  return {
    ...actual,
    onAuthStateChanged: jest.fn(),
    signOut: jest.fn(),
  };
});

global.fetch = jest.fn();

const TestComponent = () => {
  const {
    user,
    userData,
    isFullyRegistered,
    logout,
    loading,
    authError,
    setAuthError,
    isRegistering,
    setIsRegistering,
  } = useAuth();

  return (
    <div>
      <div data-testid="user">{user ? 'true' : 'false'}</div>
      <div data-testid="userData">{userData ? 'true' : 'false'}</div>
      <div data-testid="isFullyRegistered">{isFullyRegistered ? 'true' : 'false'}</div>
      <div data-testid="loading">{loading ? 'true' : 'false'}</div>
      <div data-testid="authError">{authError || 'none'}</div>
      <div data-testid="isRegistering">{isRegistering ? 'true' : 'false'}</div>
      <button onClick={() => setIsRegistering(true)}>StartRegistering</button>
      <button onClick={() => setAuthError('Some error')}>SetAuthError</button>
      <button onClick={logout}>Logout</button>
    </div>
  );
};

const renderWithProvider = () =>
  render(
    <AuthProvider>
      <TestComponent />
    </AuthProvider>
  );

describe('AuthContext', () => {
  beforeEach(() => {
    jest.clearAllMocks();
    localStorage.clear();
  });

  test('RendersWithoutUser', async () => {
    onAuthStateChanged.mockImplementation((_, cb) => {
      cb(null);
      return () => {};
    });

    await act(async () => {
      renderWithProvider();
    });

    expect(onAuthStateChanged).toHaveBeenCalled();
  });

  test('HandlesUserLoggedInSuccessfully', async () => {
    const mockUser = {
      getIdToken: jest.fn().mockResolvedValue('fake-token'),
    };

    onAuthStateChanged.mockImplementation((_, cb) => {
      cb(mockUser);
      return () => {};
    });

    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => ({ name: 'Test User' }),
    });

    const { getByTestId } = renderWithProvider();

    await waitFor(() => {
      expect(getByTestId('user').textContent).toBe('true');
      expect(getByTestId('userData').textContent).toBe('true');
      expect(getByTestId('isFullyRegistered').textContent).toBe('true');
      expect(getByTestId('loading').textContent).toBe('false');
    });
  });

  test('HandlesUserNotFound404', async () => {
    const mockUser = {
      getIdToken: jest.fn().mockResolvedValue('token'),
    };

    onAuthStateChanged.mockImplementation((_, cb) => {
      cb(mockUser);
      return () => {};
    });

    fetch.mockResolvedValueOnce({
      ok: false,
      status: 404,
    });

    const { getByTestId } = renderWithProvider();

    await waitFor(() => {
      expect(getByTestId('user').textContent).toBe('true');
      expect(getByTestId('userData').textContent).toBe('false');
      expect(getByTestId('isFullyRegistered').textContent).toBe('false');
    });
  });

  test('HandlesFetchErrorAndSignsOut', async () => {
    const mockUser = {
      getIdToken: jest.fn().mockResolvedValue('token'),
    };

    onAuthStateChanged.mockImplementation((_, cb) => {
      cb(mockUser);
      return () => {};
    });

    fetch.mockRejectedValueOnce(new Error('Fetch failed'));

    const { getByTestId } = renderWithProvider();

    await waitFor(() => {
      expect(getByTestId('authError').textContent).toBe('Fetch failed');
      expect(signOut).toHaveBeenCalled();
    });
  });

  test('SkipsFetchWhenRegistering', async () => {
    const mockUser = {
      getIdToken: jest.fn(),
    };

    onAuthStateChanged.mockImplementation((_, cb) => {
      cb(mockUser);
      return () => {};
    });

    const { getByText } = renderWithProvider();

    act(() => {
      getByText('StartRegistering').click();
    });

    await waitFor(() => {
      expect(fetch).not.toHaveBeenCalled();
    });
  });

  test('LogoutClearsState', async () => {
    const { getByText, getByTestId } = renderWithProvider();

    act(() => {
      getByText('Logout').click();
    });

    await waitFor(() => {
      expect(signOut).toHaveBeenCalled();
      expect(localStorage.getItem('token')).toBeNull();
      expect(getByTestId('user').textContent).toBe('false');
    });
  });

  test('SetAuthErrorUpdatesState', () => {
    const { getByText, getByTestId } = renderWithProvider();

    act(() => {
      getByText('SetAuthError').click();
    });

    expect(getByTestId('authError').textContent).toBe('Some error');
  });
});
