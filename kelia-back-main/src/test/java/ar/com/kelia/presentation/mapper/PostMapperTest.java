package ar.com.kelia.presentation.mapper;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.domain.model.mongo.Post;
import ar.com.kelia.infrastructure.dto.mongo.PostCreatedDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostMapperTest {

    private PostMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PostMapper();
    }

    @Test
    void testConvertDtoToPostWithAllFields() {
        User user = new User();
        user.setUid("123");

        PostCreatedDTO dto = new PostCreatedDTO();
        dto.setContent("This is a post");
        dto.setMentionedRecipes(List.of());
        dto.setMentionedProducts(List.of());
        dto.setMentionedPlaces(List.of());

        Post post = mapper.convertDtoToPost(dto, user);

        assertNotNull(post);
        assertEquals("This is a post", post.getContent());
        assertEquals(user, post.getUser());
        assertEquals(0, post.getMentionedRecipes().size());
        assertEquals(0, post.getMentionedProducts().size());
        assertEquals(0, post.getMentionedPlaces().size());
    }

    @Test
    void testConvertDtoToPostWithNullLists() {
        User user = new User();
        user.setUid("456");

        PostCreatedDTO dto = new PostCreatedDTO();
        dto.setContent("Another post");
        // listas nulas
        dto.setMentionedRecipes(null);
        dto.setMentionedProducts(null);
        dto.setMentionedPlaces(null);

        Post post = mapper.convertDtoToPost(dto, user);

        assertNotNull(post);
        assertEquals("Another post", post.getContent());
        assertEquals(user, post.getUser());

        // Deben inicializarse vacías
        assertNotNull(post.getMentionedRecipes());
        assertTrue(post.getMentionedRecipes().isEmpty());

        assertNotNull(post.getMentionedProducts());
        assertTrue(post.getMentionedProducts().isEmpty());

        assertNotNull(post.getMentionedPlaces());
        assertTrue(post.getMentionedPlaces().isEmpty());
    }
}
