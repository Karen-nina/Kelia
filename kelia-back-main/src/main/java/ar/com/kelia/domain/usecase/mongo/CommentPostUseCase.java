package ar.com.kelia.domain.usecase.mongo;

import ar.com.kelia.domain.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentPostUseCase {
    @Autowired
    private CommentService service;

    public void execute(String post_id, String uid, String content){
        this.service.comment(post_id , uid, content);
    }
}
