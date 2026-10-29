package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.domain.model.mongo.Comment;
import ar.com.kelia.domain.model.mongo.Post;
import ar.com.kelia.domain.model.mongo.UserMongo;
import ar.com.kelia.domain.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CommentService {
    private final PostRepository repository;
    private final UserService userService;

    public CommentService(PostRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public void comment(String post_id, String uid, String content) {
        try {
            if(userService.findByUid(uid).isPresent()){
                Optional<Post> optionalPost = repository.findById(post_id);
                if (optionalPost.isPresent()) {
                    Post post = optionalPost.get();
                    Comment comment = new Comment();
                    User user = userService.findByUid(uid).get();
                    UserMongo userMongo = new UserMongo();
                    userMongo.setAvatar(user.getAvatar());
                    userMongo.setUsername(user.getUsername());
                    userMongo.setDisplayName(user.getDisplayName());
                    userMongo.setUid(user.getUid());
                    comment.setContent(content);
                    comment.setUser(userMongo);
                    comment.setCreatedAt(LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")));
                    if (post.getComments() == null) {
                        post.setComments(new ArrayList<>());
                    }
                    post.getComments().add(comment);
                    repository.save(post);
            }
                }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
