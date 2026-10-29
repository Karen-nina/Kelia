package ar.com.kelia.domain.usecase.mongo;

import ar.com.kelia.domain.model.mongo.Post;
import ar.com.kelia.domain.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreatePostUseCaseTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private CreatePostUseCase createPostUseCase;

    @Test
    void shouldCallSaveOnServiceWithCorrectPost() {
        // Arrange
        Post post = new Post();
        post.setId("post-123");

        // Act
        createPostUseCase.execute(post);

        // Assert
        verify(postService).save(post);
    }
}

