package ar.com.kelia.domain.usecase.mongo;

import ar.com.kelia.domain.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentPostUseCaseTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentPostUseCase commentPostUseCase;

    @Test
    void shouldCallCommentOnServiceWithCorrectParameters() {
        // Arrange
        String postId = "post-123";
        String uid = "user-456";
        String content = "Esto es un comentario";

        // Act
        commentPostUseCase.execute(postId, uid, content);

        // Assert
        verify(commentService).comment(postId, uid, content);
    }
}
