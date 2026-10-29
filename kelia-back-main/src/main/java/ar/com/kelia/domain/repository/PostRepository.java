package ar.com.kelia.domain.repository;

import ar.com.kelia.domain.model.mongo.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    void save(Post post);
    List<Post> findByKeywordAnywhere(String keyword);
    void addLike(String postId, String userId);
    List<Post> findByAuthorId(String authorId);
    Optional<Post> findById(String postId);
    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findRandom();
}

