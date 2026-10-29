package ar.com.kelia.domain.usecase.mongo;

import ar.com.kelia.domain.model.mongo.Post;
import ar.com.kelia.domain.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPostsFromFilterUseCase {
    @Autowired
    private PostService service;

    public List<Post> execute(String filter){
        return this.service.getPostFromFilter(filter);
    }
}
