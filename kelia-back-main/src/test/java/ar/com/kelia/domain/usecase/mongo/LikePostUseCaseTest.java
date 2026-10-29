package ar.com.kelia.domain.usecase.mongo;

import ar.com.kelia.domain.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LikePostUseCaseTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private LikePostUseCase likePostUseCase;

    @Test
    void shouldCallLikePostOnService() {
        // Arrange
        String postId = "post-123";
        String uid = "user-456";

        // Act
        likePostUseCase.execute(postId, uid);

        // Assert
        verify(postService).likePost(postId, uid);
    }
}
