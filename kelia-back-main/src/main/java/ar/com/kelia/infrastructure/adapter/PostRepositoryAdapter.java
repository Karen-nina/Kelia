package ar.com.kelia.infrastructure.adapter;

import ar.com.kelia.domain.model.mongo.Post;
import ar.com.kelia.domain.repository.PostRepository;
import ar.com.kelia.infrastructure.repositoryImpl.mongo.MongoPostRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryAdapter implements PostRepository {

    private final MongoPostRepository mongoRepository;

    public PostRepositoryAdapter(MongoPostRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public void save(Post post) {
        mongoRepository.save(post);
    }

    @Override
    public List<Post> findByKeywordAnywhere(String keyword) {
        return mongoRepository.findByKeywordAnywhere(keyword);
    }

    @Override
    public void addLike(String postId, String userId) {
        mongoRepository.addLike(postId, userId);
    }

    @Override
    public List<Post> findByAuthorId(String authorId) {
        return mongoRepository.findByAuthorId(authorId);
    }

    @Override
    public Optional<Post> findById(String postId) {
        return mongoRepository.findById(postId);
    }

    @Override
    public List<Post> findRandom() { return mongoRepository.findRandom(); }

    @Override
    public List<Post> findAllByOrderByCreatedAtDesc() {
        return mongoRepository.findAllByOrderByCreatedAtDesc();
    }
}
