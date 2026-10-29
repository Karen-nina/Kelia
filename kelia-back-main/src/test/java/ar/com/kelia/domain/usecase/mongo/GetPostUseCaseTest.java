package ar.com.kelia.domain.usecase.mongo;

import ar.com.kelia.domain.model.mongo.Post;
import ar.com.kelia.domain.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPostUseCaseTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private GetPostUseCase getPostUseCase;

    @Test
    void shouldCallServiceAndReturnPostsInDescendingOrder() {
        // Arrange
        List<Post> expectedPosts = List.of(new Post(), new Post());
        when(postService.findAllByOrderByCreatedAtDesc()).thenReturn(expectedPosts);

        // Act
        List<Post> result = getPostUseCase.execute();

        // Assert
        assertThat(result).isEqualTo(expectedPosts);
        verify(postService).findAllByOrderByCreatedAtDesc();
    }
}
