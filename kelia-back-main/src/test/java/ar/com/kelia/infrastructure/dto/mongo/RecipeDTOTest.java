package ar.com.kelia.infrastructure.dto.mongo;

import ar.com.kelia.infrastructure.dto.UserDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class RecipeDTOTest {

    @Test
    void testRecipeDTOAllArgsConstructorAndGetters() {
        // Arrange
        String id = "123";
        String title = "Torta de chocolate";
        String category = "Postres";

        UserDTO user = new UserDTO();
        user.setUid("u1");
        user.setDisplayName("Juan Perez");
        user.setUsername("jperez");
        user.setBirthDate(new Date());
        user.setAvatar("avatar.png");
        user.setZipCode("1000");

        var ingredients = Arrays.asList("Harina", "Huevos", "Chocolate");
        var thumbnail = "thumb.png";
        var instructions = Arrays.asList("Mezclar", "Hornear");

        var review = new ReviewDTO();
        review.setRating(5);
        review.setComment("Excelente");
        var reviews = Collections.singletonList(review);

        int preparationTime = 45;
        int saveCount = 10;
        double rating = 4.5;
        var tags = Arrays.asList("dulce", "festivo");
        LocalDateTime createdAt = LocalDateTime.now();

        // Act
        RecipeDTO recipeDTO = new RecipeDTO(
                id, title, category, user, ingredients, thumbnail, instructions,
                reviews, preparationTime, saveCount, rating, tags, createdAt
        );

        // Assert
        assertThat(recipeDTO.getId()).isEqualTo(id);
        assertThat(recipeDTO.getTitle()).isEqualTo(title);
        assertThat(recipeDTO.getCategory()).isEqualTo(category);
        assertThat(recipeDTO.getUser()).isEqualTo(user);
        assertThat(recipeDTO.getIngredients()).containsExactly("Harina", "Huevos", "Chocolate");
        assertThat(recipeDTO.getThumbnail()).isEqualTo(thumbnail);
        assertThat(recipeDTO.getInstructions()).containsExactly("Mezclar", "Hornear");
        assertThat(recipeDTO.getReviews()).hasSize(1);
        assertThat(recipeDTO.getPreparationTime()).isEqualTo(preparationTime);
        assertThat(recipeDTO.getSave_count()).isEqualTo(saveCount);
        assertThat(recipeDTO.getRating()).isEqualTo(rating);
        assertThat(recipeDTO.getTags()).containsExactly("dulce", "festivo");
        assertThat(recipeDTO.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void testRecipeDTOSettersAndGetters() {
        // Arrange
        RecipeDTO recipeDTO = new RecipeDTO();

        // Act
        recipeDTO.setId("999");
        recipeDTO.setTitle("Pizza");
        recipeDTO.setCategory("Comida rápida");

        UserDTO userDTO = new UserDTO();
        userDTO.setUid("u2");
        recipeDTO.setUser(userDTO);

        recipeDTO.setIngredients(Collections.singletonList("Queso"));
        recipeDTO.setThumbnail("pizza.png");
        recipeDTO.setInstructions(Collections.singletonList("Hornear"));
        recipeDTO.setReviews(Collections.emptyList());
        recipeDTO.setPreparationTime(20);
        recipeDTO.setSave_count(5);
        recipeDTO.setRating(3.8);
        recipeDTO.setTags(Collections.singletonList("salado"));
        recipeDTO.setCreatedAt(LocalDateTime.now());

        // Assert
        assertThat(recipeDTO.getId()).isEqualTo("999");
        assertThat(recipeDTO.getTitle()).isEqualTo("Pizza");
        assertThat(recipeDTO.getCategory()).isEqualTo("Comida rápida");
        assertThat(recipeDTO.getUser()).isEqualTo(userDTO);
        assertThat(recipeDTO.getIngredients()).containsExactly("Queso");
        assertThat(recipeDTO.getThumbnail()).isEqualTo("pizza.png");
        assertThat(recipeDTO.getInstructions()).containsExactly("Hornear");
        assertThat(recipeDTO.getReviews()).isEmpty();
        assertThat(recipeDTO.getPreparationTime()).isEqualTo(20);
        assertThat(recipeDTO.getSave_count()).isEqualTo(5);
        assertThat(recipeDTO.getRating()).isEqualTo(3.8);
        assertThat(recipeDTO.getTags()).containsExactly("salado");
        assertThat(recipeDTO.getCreatedAt()).isNotNull();
    }
}

