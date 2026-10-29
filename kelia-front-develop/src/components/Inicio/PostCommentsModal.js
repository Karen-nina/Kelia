import React, { useState } from "react";
import Post from "./Post";
import "./PostComment.css";
import { useAuth } from "../../context/AuthContext";
import axios from "axios";

const PostCommentsModal = ({ post, onClose, timeAgo }) => {
  const { user } = useAuth();
  const [commentText, setCommentText] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);

  const baseUrl = process.env.REACT_APP_BACKEND_URL;

  const handleComment = async () => {
    setError("");
    setSuccess(false);
    if (!user) {
      setError("Debes iniciar sesión para comentar.");
      return;
    }
    if (!commentText.trim()) {
      setError("El comentario no puede estar vacío.");
      return;
    }
    setLoading(true);
    try {
      const token = await user.getIdToken();
      await axios.post(
        `${baseUrl}/comment`,
        {
          post_id: post.id || post._id,
          uid: user.uid,
          content: commentText,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );
      setSuccess(true);
      setCommentText("");
      onClose();
    } catch (err) {
      setError("No se pudo publicar el comentario. Intenta nuevamente.");
    } finally {
      setLoading(false);
    }
  };

  if (!post) return null;

  return (
    <div className="modal-backdrop" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <button className="close-btn" onClick={onClose}>
          ×
        </button>
        <Post {...post} date={timeAgo(post.createdAt)} />
        <div className="comments-section">
          <h6>Comentarios</h6>
          {post.comments && post.comments.length > 0 ? (
            post.comments.map((comment, idx) => (
              <div key={idx} className="comment d-flex align-items-start gap-2">
                <img
                  src={comment?.user?.avatar || "/img/profile-placeholder.png"}
                  alt={comment?.user?.displayName}
                  className="rounded-circle"
                  style={{ width: 36, height: 36, objectFit: "cover" }}
                />
                <div style={{ flex: 1 }}>
                  <div
                    style={{ display: "flex", alignItems: "center", gap: 8 }}
                  >
                    <strong>{comment?.user?.displayName}</strong> @
                    {comment?.user?.username}
                    <span style={{ fontSize: "0.85em", color: "#aaa" }}>
                      • {comment.createdAt ? timeAgo(comment.createdAt) : ""}
                    </span>
                  </div>
                  <div>{comment.content}</div>
                  <div
                    style={{
                      display: "flex",
                      justifyContent: "flex-end",
                      alignItems: "center",
                      fontSize: "0.95em",
                      color: "#888",
                      marginTop: 2,
                    }}
                  >
                    <i
                      className="bi bi-heart"
                      style={{ verticalAlign: "middle" }}
                    ></i>
                    <span style={{ marginLeft: 4 }}>{comment.likes}</span>
                  </div>
                </div>
              </div>
            ))
          ) : (
            <p className="text-muted">No hay comentarios aún.</p>
          )}

          <div
            style={{
              borderTop: "1px solid #eee",
              paddingTop: 16,
              marginTop: 16,
            }}
          >
            <div style={{ display: "flex", gap: 8 }}>
              <textarea
                className="form-control"
                placeholder="Escribe un comentario..."
                style={{ flex: 1, resize: "none" }}
                rows={2}
                value={commentText}
                onChange={(e) => setCommentText(e.target.value)}
              />
              <button
                className="comment-button btn btn-primary "
                onClick={handleComment}
                disabled={loading}
              >
                {loading ? "Comentando..." : "Comentar"}
              </button>
            </div>
          </div>
          {error && <p className="text-danger mt-2 mb-0">{error}</p>}
          {success && (
            <p className="text-success mt-2 mb-0">¡Comentario publicado!</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default PostCommentsModal;
