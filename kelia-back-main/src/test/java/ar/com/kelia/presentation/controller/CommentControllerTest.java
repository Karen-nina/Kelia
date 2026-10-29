package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.usecase.mongo.CommentPostUseCase;
import ar.com.kelia.infrastructure.dto.mongo.CommentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentPostUseCase useCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCommentCreatesSuccessfully() throws Exception {
        // preparar DTO
        CommentDTO dto = new CommentDTO();
        dto.setPost_id("post123");
        dto.setUid("user456");
        dto.setContent("Nice post!");

        // mock de useCase.execute (no hace nada)
        doNothing().when(useCase).execute("post123", "user456", "Nice post!");

        mockMvc.perform(post("/api/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("post comment-> post123"));

        // verificar que el use case fue llamado
        verify(useCase).execute("post123", "user456", "Nice post!");
    }

    @Test
    void testCommentBadRequestWhenBodyIsMissing() throws Exception {
        mockMvc.perform(post("/api/comment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}

