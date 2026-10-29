package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.User;
import ar.com.kelia.domain.model.mongo.Comment;
import ar.com.kelia.domain.model.mongo.Post;
import ar.com.kelia.domain.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    private PostRepository postRepository;
    private UserService userService;
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        userService = mock(UserService.class);
        commentService = new CommentService(postRepository, userService);
    }

    @Test
    void testCommentAddsCommentToPost() {
        // Caso exitoso: usuario y post existen, se agrega comentario
        String postId = "post1";
        String uid = "user123";
        String text = "Muy buena receta!";

        User user = new User();
        user.setId(1L);
        user.setDisplayName("Karen");

        Post post = new Post();
        post.setComments(new ArrayList<>());

        when(userService.findByUid(uid)).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        commentService.comment(postId, uid, text);

        verify(postRepository).save(any(Post.class));
        assertFalse(post.getComments().isEmpty());
        assertEquals(text, post.getComments().get(0).getContent());
    }

    @Test
    void testCommentUsuarioNoEncontrado() {
        // Caso: el usuario no existe, no se guarda nada
        when(userService.findByUid("noexiste")).thenReturn(Optional.empty());
        commentService.comment("post", "noexiste", "comentario");
        verify(postRepository, never()).save(any());
    }

    @Test
    void testCommentPostNoEncontrado() {
        // Caso: el post no existe, no se guarda nada
        String uid = "user123";
        User user = new User();
        user.setId(1L);
        user.setDisplayName("Karen");
        when(userService.findByUid(uid)).thenReturn(Optional.of(user));
        when(postRepository.findById("noexiste")).thenReturn(Optional.empty());
        commentService.comment("noexiste", uid, "comentario");
        verify(postRepository, never()).save(any());
    }

    @Test
    void testCommentConCommentsNulo() {
        // Caso: el post tiene comments nulo, se inicializa la lista
        String uid = "user123";
        User user = new User();
        user.setId(1L);
        user.setDisplayName("Karen");
        Post post = new Post();
        post.setComments(null);
        when(userService.findByUid(uid)).thenReturn(Optional.of(user));
        when(postRepository.findById("post")).thenReturn(Optional.of(post));
        commentService.comment("post", uid, "comentario");
        verify(postRepository).save(any(Post.class));
        assertNotNull(post.getComments());
        assertEquals(1, post.getComments().size());
    }

    @Test
    void testCommentExcepcionEnRepositorio() {
        // Caso: el repositorio lanza excepción, se propaga como RuntimeException
        String uid = "user123";
        User user = new User();
        user.setId(1L);
        user.setDisplayName("Karen");
        Post post = new Post();
        post.setComments(new ArrayList<>());
        when(userService.findByUid(uid)).thenReturn(Optional.of(user));
        when(postRepository.findById("post")).thenReturn(Optional.of(post));
        doThrow(new RuntimeException("Error al guardar")).when(postRepository).save(any());
        assertThrows(RuntimeException.class, () -> commentService.comment("post", uid, "comentario"));
    }

    @Test
    void testCommentExcepcionEnServicio() {
        // Caso: el servicio de usuario lanza excepción, se propaga como
        // RuntimeException
        when(userService.findByUid(any())).thenThrow(new RuntimeException("Error en servicio"));
        assertThrows(RuntimeException.class, () -> commentService.comment("post", "uid", "comentario"));
    }
}
