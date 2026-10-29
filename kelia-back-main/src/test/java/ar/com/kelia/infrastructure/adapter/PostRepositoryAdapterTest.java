package ar.com.kelia.infrastructure.adapter;

import ar.com.kelia.domain.model.mongo.Post;
import ar.com.kelia.infrastructure.repositoryImpl.mongo.MongoPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostRepositoryAdapterTest {
    private MongoPostRepository mongoRepository;
    private PostRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        mongoRepository = mock(MongoPostRepository.class);
        adapter = new PostRepositoryAdapter(mongoRepository);
    }

    @Test
    void testSave() {
        Post post = new Post();
        adapter.save(post);
        verify(mongoRepository).save(post);
    }

    @Test
    void testFindByKeywordAnywhere() {
        List<Post> posts = List.of(new Post());
        when(mongoRepository.findByKeywordAnywhere("test")).thenReturn(posts);
        assertEquals(posts, adapter.findByKeywordAnywhere("test"));
    }

    @Test
    void testAddLike() {
        adapter.addLike("postId", "userId");
        verify(mongoRepository).addLike("postId", "userId");
    }

    @Test
    void testFindByAuthorId() {
        List<Post> posts = List.of(new Post());
        when(mongoRepository.findByAuthorId("authorId")).thenReturn(posts);
        assertEquals(posts, adapter.findByAuthorId("authorId"));
    }

    @Test
    void testFindById() {
        Post post = new Post();
        when(mongoRepository.findById("postId")).thenReturn(Optional.of(post));
        Optional<Post> result = adapter.findById("postId");
        assertTrue(result.isPresent());
        assertEquals(post, result.get());
    }

    @Test
    void testFindRandom() {
        List<Post> posts = List.of(new Post());
        when(mongoRepository.findRandom()).thenReturn(posts);
        assertEquals(posts, adapter.findRandom());
    }

    @Test
    void testFindAllByOrderByCreatedAtDesc() {
        List<Post> posts = List.of(new Post());
        when(mongoRepository.findAllByOrderByCreatedAtDesc()).thenReturn(posts);
        assertEquals(posts, adapter.findAllByOrderByCreatedAtDesc());
    }
}