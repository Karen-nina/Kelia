import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import { createUserWithEmailAndPassword, signOut } from 'firebase/auth';
import { auth } from '../../firebase';
import { useAuth } from '../../context/AuthContext';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './Register.css';

const Register = () => {
  const [email, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [displayName, setDisplayName] = useState('');
  const [birthDate, setBirthDate] = useState('');
  const [zipCode, setZipCode] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const { setIsRegistering } = useAuth();

  const baseUrl = process.env.REACT_APP_BACKEND_URL;

  const handleRegister = async (e) => {
    e.preventDefault();
    setError('');
    setIsRegistering(true);

    try {
      const userCredential = await createUserWithEmailAndPassword(auth, email, password);
      const user = userCredential.user;
      const token = await user.getIdToken();
      const uid = user.uid;

      await axios.post(`${baseUrl}/user`, {
        uid,
        displayName,
        username,
        avatar: "",
        birthDate: new Date(birthDate).toISOString(),
        zipCode,
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });

      await signOut(auth);
      toast.success('¡Te registraste exitosamente! Por favor inicia sesión.', {
        position: 'top-right',
        autoClose: 2000,
      });

      setTimeout(() => {
        navigate('/login');
      }, 2000);
    } catch (err) {
      if (auth.currentUser) {
        await auth.currentUser.delete();
      }
      console.error('Error en el registro completo:', err);
      setError('No se pudo crear la cuenta. Verifica los datos o intenta más tarde.');
      toast.error('Error al registrar. Intenta nuevamente.', {
        position: 'top-right',
        autoClose: 3000,
      });
    } finally {
      setIsRegistering(false);
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center py-3" style={{ backgroundColor: '#F3F1E6' }}>
      <div className="card p-4" style={{ width: '100%', maxWidth: '400px', borderColor: '#9bb084', borderWidth: '2px', borderStyle: 'solid' }}>
        <h3 className="text-center mb-4">Crear Cuenta</h3>

        <form onSubmit={handleRegister} autoComplete="off">
          <div className="mb-3">
            <label htmlFor="email" className="form-label">Correo Electrónico</label>
            <input
              type="email"
              className={`form-control campo-mail-register ${email ? 'completo' : ''}`}
              id="email"
              placeholder="usuario@dominio.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label htmlFor="username" className="form-label">Nombre de usuario</label>
            <input
              type="text"
              className="campo-name-register form-control"
              id="username"
              placeholder="José Pérez"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label htmlFor="password" className="form-label">Contraseña</label>
            <input
              type="password"
              className="campo-pass-register form-control"
              id="password"
              placeholder="********"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label htmlFor="displayName" className="form-label">¿Cómo quieres que te llamemos?</label>
            <input
              type="text"
              className="campo-preg-register form-control"
              id="displayName"
              placeholder="Juan Pérez"
              value={displayName}
              onChange={(e) => setDisplayName(e.target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label htmlFor="birthDate" className="form-label">Fecha de nacimiento</label>
            <input
              type="date"
              className={`form-control campo-date-register ${birthDate ? 'completo' : ''}`}
              id="birthDate"
              placeholder="dd/mm/aaaa"
              value={birthDate}
              onChange={(e) => setBirthDate(e.target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label htmlFor="zipCode" className="form-label">Código Postal</label>
            <input
              type="text"
              className="campo-code-register form-control"
              id="zipCode"
              placeholder="Ej: 1754"
              value={zipCode}
              onChange={(e) => setZipCode(e.target.value)}
              required
            />
          </div>

          <button type="submit" className="button-registrar btn w-100">
            Registrarse
          </button>
        </form>

        {error && (
          <p className="text-danger text-center mt-3">{error}</p>
        )}

        <p className="text-center mt-3">
          ¿Ya tienes cuenta? <Link to="/login" className="button-iniciar-sesion">Iniciar sesión</Link>
        </p>
      </div>
      <ToastContainer />
    </div>
  );
};

export default Register;