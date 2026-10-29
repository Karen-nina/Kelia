package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.mongo.Post;
import ar.com.kelia.domain.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    private PostRepository postRepository;
    private PostService postService;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        postService = new PostService(postRepository);
    }

    @Test
    void testSaveCallsRepository() {
        Post post = new Post();
        postService.save(post);
        verify(postRepository).save(post);
    }

    @Test
    void testGetPostFromFilterReturnsList() {
        List<Post> expected = List.of(new Post());
        when(postRepository.findByKeywordAnywhere("receta")).thenReturn(expected);
        List<Post> result = postService.getPostFromFilter("receta");
        assertEquals(expected, result);
    }

    @Test
    void testLikePostCallsRepository() {
        postService.likePost("p1", "u1");
        verify(postRepository).addLike("p1", "u1");
    }

    @Test
    void testFindAllByOrderByCreatedAtDescReturnsList() {
        List<Post> expected = List.of(new Post());
        when(postRepository.findAllByOrderByCreatedAtDesc()).thenReturn(expected);
        List<Post> result = postService.findAllByOrderByCreatedAtDesc();
        assertEquals(expected, result);
    }

    @Test
    void testGetPostRandomReturnsList() {
        List<Post> expected = List.of(new Post());
        when(postRepository.findRandom()).thenReturn(expected);
        List<Post> result = postService.getPostRandom();
        assertEquals(expected, result);
    }
}

