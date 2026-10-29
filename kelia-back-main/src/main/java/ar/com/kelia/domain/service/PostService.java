package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.mongo.Post;
import ar.com.kelia.domain.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public void save(Post post){
        post.setActive(1);
        repository.save(post);
    }

    public List<Post> getPostFromFilter(String name){
        if(name == null || name.trim().isEmpty()){
            return null;
        }else{
            return repository.findByKeywordAnywhere(name);
        }

    }

    public void likePost(String postId, String userId) {
        repository.addLike(postId, userId);
    }

    public List<Post> findAllByOrderByCreatedAtDesc(){
       return repository.findAllByOrderByCreatedAtDesc();
    }

    public List<Post> getPostRandom(){
        return repository.findRandom();
    }
}

