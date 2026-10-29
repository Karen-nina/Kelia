package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.domain.model.mongo.Place;
import ar.com.kelia.domain.model.mongo.Recipe;
import ar.com.kelia.domain.model.mongo.Review;
import ar.com.kelia.domain.model.mongo.UserMongo;
import ar.com.kelia.domain.repository.PlaceRepository;
import ar.com.kelia.domain.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ReviewService {
    private final UserService service;
    private final RecipeRepository recipeRepository;
    private final PlaceRepository placeRepository;

    public ReviewService(UserService service, RecipeRepository recipeRepository, PlaceRepository placeRepository) {
        this.service = service;
        this.recipeRepository = recipeRepository;
        this.placeRepository = placeRepository;
    }

    public void reviewRecipe(String recipeId, String uid, String text, int rating) {
        try {
            if(service.findByUid(uid).isPresent()){
                Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
                if (optionalRecipe.isPresent()) {
                    Recipe recipe = optionalRecipe.get();
                    Review recipeReview = new Review();
                    User user = service.findByUid(uid).get();
                    UserMongo userMongo = new UserMongo();
                    userMongo.setAvatar(user.getAvatar());
                    userMongo.setUsername(user.getUsername());
                    userMongo.setDisplayName(user.getDisplayName());
                    userMongo.setUid(user.getUid());
                    recipeReview.setComment(text);
                    recipeReview.setUser(userMongo);
                    recipeReview.setRating(rating);
                    recipeReview.setCreatedAt(LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")));

                    if (recipe.getReviews() == null) {
                        recipe.setReviews(new ArrayList<>());
                    }

                    recipe.getReviews().add(recipeReview);
                    recipeRepository.save(recipe);
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void reviewPlace(String placeId, String uid, String text, int rating) {
        try {
            if(service.findByUid(uid).isPresent()){
                Optional<Place> optionalPlace = placeRepository.findById(placeId);
                if (optionalPlace.isPresent()) {
                    Place place = optionalPlace.get();
                    Review review = new Review();
                    User user = service.findByUid(uid).get();
                    UserMongo userMongo = new UserMongo();
                    userMongo.setAvatar(user.getAvatar());
                    userMongo.setUsername(user.getUsername());
                    userMongo.setDisplayName(user.getDisplayName());
                    userMongo.setUid(user.getUid());
                    review.setComment(text);
                    review.setUser(userMongo);
                    review.setRating(rating);
                    review.setCreatedAt(LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")));

                    if (place.getReviews() == null) {
                        place.setReviews(new ArrayList<>());
                    }

                    place.getReviews().add(review);
                    placeRepository.save(place);
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
