package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.usecase.mongo.LikePostUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikeController.class)
@TestPropertySource(properties = {
        "spring.security.user.name=test",
        "spring.security.user.password=test"
})
class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LikePostUseCase useCase;

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
    void testLikePost() throws Exception {
        mockMvc.perform(post("/api/like")
                .with(csrf())
                .param("postId", "p456")
                .param("uid", "abc123"))
                .andExpect(status().isCreated());

        verify(useCase).execute("p456", "abc123");
    }
}
