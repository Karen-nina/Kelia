import React from 'react';
import axios from "axios";
import { useAuth } from '../../context/AuthContext';

const baseUrl = process.env.REACT_APP_BACKEND_URL;

const PostForm = ({ isFocused, setIsFocused }) => {
  const { user, userData } = useAuth();
  const [content, setContent] = React.useState('');
  const [loading, setLoading] = React.useState(false);
  const [error, setError] = React.useState('');
  const [success, setSuccess] = React.useState(false);

  const handlePost = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess(false);

    if (!userData) {
      setError('Debes iniciar sesión para postear.');
      return;
    }

    if (!content.trim()) {
      setError('El contenido no puede estar vacío.');
      return;
    }

    setLoading(true);

    try {
      const token = await user.getIdToken();

      await axios.post(
        `${baseUrl}/post?uid=${user.uid}`,
        {
          content,
          createdAt: new Date().toISOString(),
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );
      setSuccess(true);
      setContent('');
      setIsFocused(false);
    } catch (err) {
      console.error(err);
      setError('No se pudo publicar el post. Intenta nuevamente.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <form
      onSubmit={handlePost}
      className="mb-4 p-3"
      style={{
        backgroundColor: 'white',
        borderRadius: '10px',
        boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)',
      }}
    >
      <div className="d-flex">
        <img
          src={userData?.avatar || '/img/avatars/default.png'}
          alt="Perfil"
          className="rounded-circle"
          style={{ width: '50px', height: '50px', objectFit: 'cover' }}
        />
        <textarea
          className="form-control ms-3"
          rows={isFocused ? 5 : 1}
          placeholder="¿Qué está pasando?"
          style={{ border: 'none', resize: 'none', transition: 'all 0.3s ease' }}
          onFocus={() => setIsFocused(true)}
          onBlur={() => setIsFocused(false)}
          value={content}
          onChange={(e) => setContent(e.target.value)}
        ></textarea>
      </div>

      <div className="d-flex justify-content-end mt-3">
        <button
          type="submit"
          className="btn post-button"
          disabled={loading}
        >
          {loading ? 'Posteando...' : 'Postear'}
        </button>
      </div>

      {error && <p className="text-danger mt-2 mb-0">{error}</p>}
      {success && <p className="text-success mt-2 mb-0">¡Mensaje publicado con éxito!</p>}
    </form>
  );
};

export default PostForm;
