import React from 'react';
import { generateMentionPills } from '../../helpers/generateMentionPills';

const Post = ({
  user,
  content,
  comments,
  likes,
  date,
  mentionedRecipes = [],
  mentionedProducts = [],
  mentionedPlaces = [],
}) => {
  return (
    <div
      className="card mb-3"
      style={{
        border: '1px solid #e0e0e0',
        borderRadius: '15px',
        boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)',
      }}
    >
      <div className="card-body">
        <div className="d-flex align-items-center mb-3">
          <img
            src={user.avatar}
            alt="Perfil"
            className="rounded-circle"
            style={{ width: '50px', height: '50px', objectFit: 'cover', marginRight: '15px' }}
          />
          <div>
            <h5 className="mb-0 fw-bold" style={{ fontSize: '1rem' }}>
              {user.displayName}
            </h5>
            <span className="text-muted" style={{ fontSize: '0.9rem' }}>
              @{user.username} • {date}
            </span>
          </div>
        </div>

        <p
          style={{ fontSize: '1rem', color: '#333', lineHeight: '1.5' }}
          dangerouslySetInnerHTML={{ __html: content }}
        />

        <div className="mt-2 d-flex flex-wrap gap-2">
          {generateMentionPills({
            recipes: mentionedRecipes ?? [],
            products: mentionedProducts ?? [],
            places: mentionedPlaces ?? [],
          })}
        </div>

        <div className="d-flex justify-content-between align-items-center mt-3">
          <span style={{ fontSize: '0.9rem', color: '#888' }}>
            <i className="bi bi-chat me-1" />
            {Array.isArray(comments) ? comments.length : comments}
          </span>
          <span style={{ fontSize: '0.9rem', color: '#888' }}>
            <i className="bi bi-heart me-1" />
            {Array.isArray(likes) ? likes.length : likes}
          </span>
        </div>
      </div>
    </div>
  );
};

export default Post;