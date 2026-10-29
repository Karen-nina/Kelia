package ar.com.kelia.infrastructure.dto.mongo;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PostDTOTest {
    @Test
    void testNoArgsConstructorAndSetters() {
        PostDTO dto = new PostDTO();
        dto.setUid("uid");
        dto.setContent("contenido");
        dto.setActive(1);
        dto.setMentionedRecipes(Collections.emptyList());
        dto.setMentionedProducts(Collections.emptyList());
        dto.setMentionedPlaces(Collections.emptyList());
        LocalDateTime now = LocalDateTime.now();
        dto.setCreatedAt(now);
        assertEquals("uid", dto.getUid());
        assertEquals("contenido", dto.getContent());
        assertEquals(1, dto.getActive());
        assertEquals(now, dto.getCreatedAt());
        assertNotNull(dto.getMentionedRecipes());
        assertNotNull(dto.getMentionedProducts());
        assertNotNull(dto.getMentionedPlaces());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        PostDTO dto1 = new PostDTO();
        PostDTO dto2 = new PostDTO();
        dto1.setCreatedAt(now);
        dto2.setCreatedAt(now);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotNull(dto1.toString());
    }
}