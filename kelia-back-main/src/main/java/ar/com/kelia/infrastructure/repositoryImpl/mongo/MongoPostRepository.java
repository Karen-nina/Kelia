package ar.com.kelia.infrastructure.repositoryImpl.mongo;

import ar.com.kelia.domain.model.mongo.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoPostRepository extends MongoRepository<Post, String> {
    @Query("{ 'user.id': ?0 }")
    List<Post> findByAuthorId(String authorId);

    @Query("{ '$or': [ " +
            "  { 'content': { $regex: ?0, $options: 'i' } }, " +
            "  { 'comments.content': { $regex: ?0, $options: 'i' } }, " +
            "  { 'mentionedRecipes.title': { $regex: ?0, $options: 'i' } }, " +
            "  { 'mentionedProducts.title': { $regex: ?0, $options: 'i' } }, " +
            "  { 'mentionedPlaces.title': { $regex: ?0, $options: 'i' } } " +
            "] }")
    List<Post> findByKeywordAnywhere(String keyword);

    @Query("{ '_id': ?0 }")
    @Update("{ '$addToSet': { 'likes': ?1 } }")
    void addLike(String postId, String userId);

    List<Post> findAllByOrderByCreatedAtDesc();

    @Aggregation(pipeline = {
            "{ $sample: { size: 3 } }"
    })
    List<Post> findRandom();
}