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
class GetPostRandomUseCaseTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private GetPostRandomUseCase getPostRandomUseCase;

    @Test
    void shouldCallServiceAndReturnRandomPosts() {
        // Arrange
        List<Post> expectedPosts = List.of(new Post(), new Post(), new Post());
        when(postService.getPostRandom()).thenReturn(expectedPosts);

        // Act
        List<Post> result = getPostRandomUseCase.execute();

        // Assert
        assertThat(result).isEqualTo(expectedPosts);
        verify(postService).getPostRandom();
    }
}
