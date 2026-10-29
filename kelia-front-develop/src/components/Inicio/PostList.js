import { renderContentWithMentions } from "../../helpers/renderContentWithMentions";
import { timeAgo } from "../../helpers/timeAgo";
import PostCommentsModal from "../../components/Inicio/PostCommentsModal";
import React, { useState } from "react";
import Post from "./Post";
import { Spinner } from "react-bootstrap";

function PostList({ posts, loading, highlightTerm }) {
  const [selectedPost, setSelectedPost] = useState(null);
  const [showModal, setShowModal] = useState(false);

  if (loading) return <Spinner animation="border" size="sm"><span className="visually-hidden">Cargando...</span></Spinner>;

  if (posts.length == 0)
    return (
      <p className="text-center text-muted">No hay comentarios disponibles.</p>
    );

  return (
    <>
      {posts.map((post, index) => (
        <div
          key={index}
          onClick={() => {
            const processedPost = {
              ...post,
              content: renderContentWithMentions(post.content, highlightTerm, {
                recipes: post.mentionedRecipes,
                products: post.mentionedProducts,
                places: post.mentionedPlaces,
              }),
            };
            setSelectedPost(processedPost);
            setShowModal(true);
          }}
          style={{ cursor: "pointer" }}
        >
          <Post
            user={post.user}
            content={renderContentWithMentions(post.content, highlightTerm, {
              recipes: post.mentionedRecipes,
              products: post.mentionedProducts,
              places: post.mentionedPlaces,
            })}
            comments={post.comments}
            likes={post.likes}
            date={timeAgo(post.createdAt)}
            mentionedRecipes={post.mentionedRecipes}
            mentionedProducts={post.mentionedProducts}
            mentionedPlaces={post.mentionedPlaces}
          />
        </div>
      ))}
      {/* Modal de comentarios */}
      {showModal && (
        <PostCommentsModal
          post={selectedPost}
          onClose={() => setShowModal(false)}
          timeAgo={timeAgo}
        />
      )}
    </>
  );
}

export default PostList;
