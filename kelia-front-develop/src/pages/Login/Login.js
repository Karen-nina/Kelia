import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { signInWithEmailAndPassword } from 'firebase/auth';
import { auth } from '../../firebase';
import { useAuth } from '../../context/AuthContext';
import './Login.css';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const { authError, setAuthError } = useAuth();
  const [error, setError] = useState(authError || '');
  const navigate = useNavigate();

  useEffect(() => {
    if (authError) {
      setError(authError);
    }
  }, [authError]);

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');
    setAuthError(null);

    try {
      const userCredential = await signInWithEmailAndPassword(auth, email, password);
      const token = await userCredential.user.getIdToken();
      localStorage.setItem('token', token);
      console.log('Login exitoso:', userCredential.user);
      navigate('/inicio');
    } catch (err) {
      console.error('Error al iniciar sesión:', err);
      setError('Correo o contraseña incorrectos');
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-start mt-5 vh-100" style={{ backgroundColor: '#F3F1E6' }}>
      <div className="card p-4" style={{ width: '100%', maxWidth: '400px', borderColor: '#9bb084', borderWidth: '2px', borderStyle: 'solid' }}>
        <h3 className="text-center mb-4">Iniciar sesión</h3>
        <form onSubmit={handleLogin}>
          <div className="mb-3">
            <label htmlFor="email" className="form-label">Correo electrónico</label>
            <input
              type="email"
              className={`form-control campo-mail ${email ? 'completo' : ''}`}
              id="email"
              placeholder="usuario@dominio.com"
              value={email}
              onChange={(e) => {
                setEmail(e.target.value);
                setError('');
                setAuthError(null);
              }}
              required
            />
          </div>

          <div className="mb-3">
            <label htmlFor="password" className="form-label">Contraseña</label>
            <input
              type="password"
              className={`form-control campo-pass ${password ? 'completo' : ''}`}
              id="password"
              placeholder="********"
              value={password}
              onChange={(e) => {
                setPassword(e.target.value);
                setError('');
                setAuthError(null);
              }}
              required
            />
          </div>

          <button type="submit" className="button-ingresar btn w-100">
            Ingresar
          </button>
        </form>

        {error && (
          <p className="text-danger text-center mt-3">{error}</p>
        )}

        <p className="text-center mt-3">
          ¿No tenés cuenta? <Link className="button-registrate" to="/register">Registrate</Link>
        </p>
      </div>
    </div>
  );
};

export default Login;