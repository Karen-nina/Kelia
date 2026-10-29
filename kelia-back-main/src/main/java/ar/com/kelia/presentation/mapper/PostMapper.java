package ar.com.kelia.presentation.mapper;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.domain.model.mongo.Post;
import ar.com.kelia.domain.service.UserService;
import ar.com.kelia.infrastructure.dto.mongo.PostCreatedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;


@Component
public class PostMapper {

    public Post convertDtoToPost(PostCreatedDTO postDTO , User user) {
        Post post = new Post();
        post.setUser(user);
        post.setContent(postDTO.getContent());

        post.setMentionedRecipes(
                Optional.ofNullable(postDTO.getMentionedRecipes()).orElse(new ArrayList<>())
        );
        post.setMentionedProducts(
                Optional.ofNullable(postDTO.getMentionedProducts()).orElse(new ArrayList<>())
        );
        post.setMentionedPlaces(
                Optional.ofNullable(postDTO.getMentionedPlaces()).orElse(new ArrayList<>())
        );

        return post;
    }
}
