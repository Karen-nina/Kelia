package ar.com.kelia.presentation.mapper;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.domain.service.UserService;
import ar.com.kelia.infrastructure.dto.UserDTO;
import ar.com.kelia.infrastructure.dto.mongo.RecipeCreatedDTO;
import ar.com.kelia.infrastructure.dto.mongo.RecipeDTO;
import ar.com.kelia.infrastructure.dto.mongo.ReviewDTO;
import ar.com.kelia.domain.model.mongo.Recipe;
import ar.com.kelia.domain.model.mongo.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class RecipeMapper {

    @Autowired
    private UserService userService;

    public RecipeDTO toDTO(Recipe recipe) {

        List<ReviewDTO> reviewDTOs = recipe.getReviews() != null
                ? recipe.getReviews().stream()
                .map(review -> {
                    Optional<User> userOpt = userService.findByUid(review.getUser().getUid());

                    UserDTO userDTO = userOpt
                            .map(UserMapper::toDTO)
                            .orElseGet(() -> {
                                UserDTO fallback = new UserDTO();
                                fallback.setUid(review.getUser().getUid());
                                fallback.setDisplayName(review.getUser().getDisplayName());
                                fallback.setAvatar(review.getUser().getAvatar());
                                return fallback;
                            });

                    return new ReviewDTO(
                            userDTO,
                            review.getRating(),
                            review.getComment(),
                            review.getCreatedAt());
                })
                .collect(Collectors.toList())
                : List.of();


        double averageRating = 0.0;
        if (recipe.getReviews() != null && !recipe.getReviews().isEmpty()) {
            averageRating = recipe.getReviews().stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);
        }

        return new RecipeDTO(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getCategory(),
                UserMapper.toDTO(recipe.getUser()),
                recipe.getIngredients(),
                recipe.getThumbnail(),
                recipe.getInstructions(),
                reviewDTOs,
                recipe.getPreparationTime(),
                recipe.getSave_count(),
                averageRating,
                recipe.getTags(),
                recipe.getCreatedAt()
        );
    }
    public static Recipe toEntity(RecipeCreatedDTO dto, User user) {
        Recipe recipe = new Recipe();
        recipe.setUser(user);
        recipe.setTitle(dto.getTitle());
        recipe.setCategory(dto.getCategory());
        recipe.setCreatedAt(dto.getCreatedAt());
        recipe.setIngredients(dto.getIngredients());
        recipe.setThumbnail(dto.getThumbnail());
        recipe.setInstructions(dto.getInstructions());
        recipe.setPreparationTime(dto.getPreparationTime());
        recipe.setTags(dto.getTags());

        return recipe;
    }
}
