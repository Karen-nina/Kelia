package ar.com.kelia.domain.usecase.mongo;

import ar.com.kelia.domain.model.mongo.Post;
import ar.com.kelia.domain.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikePostUseCase {
    @Autowired
    private PostService service;

    public void execute(String postId, String uid){
        this.service.likePost(postId, uid);
    }
}
