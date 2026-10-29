package ar.com.kelia.domain.usecase.mongo;
import ar.com.kelia.domain.model.mongo.Post;
import ar.com.kelia.domain.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreatePostUseCase {
    @Autowired
    private PostService service;

    public void execute(Post post){
        this.service.save(post);
    }
}
