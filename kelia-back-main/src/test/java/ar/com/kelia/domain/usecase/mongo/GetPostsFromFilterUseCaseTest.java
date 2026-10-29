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
class GetPostsFromFilterUseCaseTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private GetPostsFromFilterUseCase getPostsFromFilterUseCase;

    @Test
    void shouldCallServiceAndReturnFilteredPosts() {
        // Arrange
        String filter = "vegan";
        List<Post> expectedPosts = List.of(new Post(), new Post());
        when(postService.getPostFromFilter(filter)).thenReturn(expectedPosts);

        // Act
        List<Post> result = getPostsFromFilterUseCase.execute(filter);

        // Assert
        assertThat(result).isEqualTo(expectedPosts);
        verify(postService).getPostFromFilter(filter);
    }
}
