package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.domain.model.mongo.Post;
import ar.com.kelia.domain.service.PostService;
import ar.com.kelia.domain.service.UserService;
import ar.com.kelia.domain.usecase.mongo.CreatePostUseCase;
import ar.com.kelia.domain.usecase.mongo.GetPostRandomUseCase;
import ar.com.kelia.domain.usecase.mongo.GetPostUseCase;
import ar.com.kelia.domain.usecase.mongo.GetPostsFromFilterUseCase;
import ar.com.kelia.infrastructure.dto.mongo.PostCreatedDTO;
import ar.com.kelia.presentation.mapper.PostMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@TestPropertySource(properties = {
        "spring.security.user.name=test",
        "spring.security.user.password=test"
})
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private CreatePostUseCase createPostUseCase;

    @MockBean
    private GetPostsFromFilterUseCase getPostsFromFilterUseCase;

    @MockBean
    private GetPostUseCase getPostUseCase;

    @MockBean
    private GetPostRandomUseCase getPostRandomUseCase;

    @MockBean
    private PostMapper postMapper;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private RequestPostProcessor bearerToken(String uid) {
        return request -> {
            request.addHeader("Authorization", "Bearer dummy-token");
            request.setAttribute("user_id", uid);
            return request;
        };
    }

    @Test
    @WithMockUser
    void testGetAllPosts() throws Exception {
        // Configurar mocks para el test
        List<Post> posts = List.of(new Post());
        when(getPostUseCase.execute()).thenReturn(posts);

        mockMvc.perform(get("/api/post"))
                .andExpect(status().isOk());

        verify(getPostUseCase).execute();
    }

    @Test
    @WithMockUser
    void testCreatePost() throws Exception {
        // Configurar mocks para el test
        User user = new User();
        user.setUid("abc123");
        user.setDisplayName("Test User");

        PostCreatedDTO postDTO = new PostCreatedDTO();
        postDTO.setContent("Test Post Content");

        Post post = new Post();
        post.setContent("Test Post Content");

        when(userService.findByUid("abc123")).thenReturn(Optional.of(user));
        when(postMapper.convertDtoToPost(any(PostCreatedDTO.class), any(User.class))).thenReturn(post);
        doNothing().when(createPostUseCase).execute(any(Post.class));

        mockMvc.perform(post("/api/post?uid=abc123")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void testCreatePostUsuarioNoEncontrado() throws Exception {
        // Caso: el usuario no existe
        PostCreatedDTO postDTO = new PostCreatedDTO();
        postDTO.setContent("Test Post Content");
        // Simular usuario no encontrado
        when(userService.findByUid("noexiste")).thenReturn(Optional.empty());
        mockMvc.perform(post("/api/post?uid=noexiste")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void testGetPostsForParamSinResultados() throws Exception {
        // Caso: búsqueda sin resultados
        when(getPostsFromFilterUseCase.execute("nada")).thenReturn(List.of());
        mockMvc.perform(get("/api/post?term=nada"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testGetPostsForParamTermVacio() throws Exception {
        // Caso: term vacío, retorna todos los posts
        when(getPostUseCase.execute()).thenReturn(List.of());
        mockMvc.perform(get("/api/post?term="))
                .andExpect(status().isNotFound());
    }
}
