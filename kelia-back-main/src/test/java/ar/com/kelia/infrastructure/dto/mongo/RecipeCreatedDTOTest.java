package ar.com.kelia.infrastructure.dto.mongo;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCreatedDTOTest {
    @Test
    void testNoArgsConstructorAndSetters() {
        RecipeCreatedDTO dto = new RecipeCreatedDTO();
        dto.setTitle("title");
        dto.setUid("uid");
        dto.setCategory("cat");
        dto.setIngredients(Collections.singletonList("ingr"));
        dto.setThumbnail("thumb");
        dto.setInstructions(Collections.singletonList("step"));
        dto.setPreparationTime(10);
        dto.setTags(Collections.singletonList("tag"));
        dto.setCreatedAt(LocalDateTime.now());
        assertEquals("title", dto.getTitle());
        assertEquals("uid", dto.getUid());
        assertEquals("cat", dto.getCategory());
        assertEquals(10, dto.getPreparationTime());
        assertNotNull(dto.getTags());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        RecipeCreatedDTO dto = new RecipeCreatedDTO("title", "uid", "cat", Collections.singletonList("ingr"), "thumb",
                Collections.singletonList("step"), 10, Collections.singletonList("tag"), now);
        assertEquals("title", dto.getTitle());
        assertEquals("uid", dto.getUid());
        assertEquals("cat", dto.getCategory());
        assertEquals(10, dto.getPreparationTime());
        assertEquals(now, dto.getCreatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        RecipeCreatedDTO dto1 = new RecipeCreatedDTO("title", "uid", "cat", Collections.singletonList("ingr"), "thumb",
                Collections.singletonList("step"), 10, Collections.singletonList("tag"), now);
        RecipeCreatedDTO dto2 = new RecipeCreatedDTO("title", "uid", "cat", Collections.singletonList("ingr"), "thumb",
                Collections.singletonList("step"), 10, Collections.singletonList("tag"), now);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotNull(dto1.toString());
    }
}