import React from 'react';
import { createContext, useContext, useEffect, useState } from 'react';
import { onAuthStateChanged, signOut } from 'firebase/auth';
import { auth } from '../firebase';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [userData, setUserData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [isFullyRegistered, setIsFullyRegistered] = useState(false);
  const [authError, setAuthError] = useState(null);
  const [isRegistering, setIsRegistering] = useState(false);

  const baseUrl = process.env.REACT_APP_BACKEND_URL;

  useEffect(() => {
    const unsub = onAuthStateChanged(auth, async (firebaseUser) => {
      setAuthError(null);
      setUser(firebaseUser);
      setUserData(null);
      setIsFullyRegistered(false);

      if (firebaseUser && !isRegistering) {
        try {
          const token = await firebaseUser.getIdToken();
          const res = await fetch(`${baseUrl}/user/me`, {
            method: 'GET',
            headers: { Authorization: `Bearer ${token}` },
          });

          if (!res.ok) {
            if (res.status === 404) {
              console.log("Usuario no encontrado aún, probablemente aún no creado.");
              return;
            } else {
              throw new Error('Error al obtener datos del usuario.');
            }
          }

          const data = await res.json();
          setUserData(data);
          setIsFullyRegistered(true);

        } catch (error) {
          await signOut(auth);
          setUser(null);
          setUserData(null);
          setIsFullyRegistered(false);
          setAuthError(error.message || 'Error desconocido al autenticar.');
        }
      }

      setLoading(false);
    });

    return () => unsub();
  }, []);

  const logout = async () => {
    try {
      await signOut(auth);
      setUser(null);
      setUserData(null);
      setIsFullyRegistered(false);
      localStorage.removeItem("token");
    } catch (error) {
      console.error("Error al cerrar sesión:", error);
    }
  };

  return (
    <AuthContext.Provider value={{
      user,
      userData,
      isFullyRegistered,
      logout,
      loading,
      authError,
      setAuthError,
      isRegistering,
      setIsRegistering,
    }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);