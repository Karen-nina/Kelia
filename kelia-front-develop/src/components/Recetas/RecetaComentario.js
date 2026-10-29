import React, { useState } from "react";
import axios from "axios";
import { useAuth } from "../../context/AuthContext";
import EditableStars from "../EditableStars/EditableStars";
import "./RecetasTarjetas.css";

const baseUrl = process.env.REACT_APP_BACKEND_URL;

const RecetaComentario = ({ recipeId, onCommentAdded }) => {
  const { user, userData } = useAuth();
  const [text, setText] = useState("");
  const [rating, setRating] = useState(0);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess(false);
    if (!user) {
      setError("Debes iniciar sesión para comentar.");
      return;
    }
    if (!text.trim() || rating === 0) {
      setError("Completa el comentario y la valoración.");
      return;
    }
    setLoading(true);
    try {
      const token = await user.getIdToken();

      await axios.post(
        `${baseUrl}/review`,
        {
          recipeId,
          rating,
          uid: user.uid,
          content: text,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );
      setSuccess(true);
      setText("");
      setRating(0);
      onCommentAdded();
    } catch (err) {
      setError("No se pudo publicar el comentario. Intenta nuevamente.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form
      className="comentario"
      onSubmit={handleSubmit}
      style={{
        border: "1px solid #eee",
        borderRadius: 8,
        marginTop: 16,
        padding: 16,
      }}
    >
      <div className="comentario-autor">
        <img
          src={userData?.avatar || "/img/avatars/default.png"}
          alt={userData?.displayName || "Usuario"}
          className="foto-perfil-comentario"
        />
        <div className="info-autor">
          <p className="nombre-autor">{userData?.displayName || "Usuario"}</p>
          <EditableStars value={rating} onChange={setRating} />
        </div>
      </div>
      <div className="comentario-texto">
        <textarea
          className="form-control"
          placeholder="Escribe tu comentario..."
          value={text}
          onChange={(e) => setText(e.target.value)}
          rows={3}
          style={{ resize: "vertical", marginTop: 8 }}
        />
      </div>
      <div className="d-flex justify-content-end align-items-center mt-2">
        <button
          type="submit"
          className="button-comentar btn-success"
          disabled={loading}
          style={{ minWidth: 120 }}
        >
          {loading ? "Publicando..." : "Publicar"}
        </button>
      </div>
      {error && <p className="text-danger mt-2 mb-0">{error}</p>}
      {success && (
        <p className="text-success mt-2 mb-0">¡Comentario publicado!</p>
      )}
    </form>
  );
};

export default RecetaComentario;
