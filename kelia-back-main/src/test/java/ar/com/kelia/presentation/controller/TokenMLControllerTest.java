package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.usecase.SaveTokenMLUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TokenMLController.class)
@TestPropertySource(properties = {
        "spring.security.user.name=test",
        "spring.security.user.password=test"
})
class TokenMLControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SaveTokenMLUseCase saveTokenMLUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testSaveToken() throws Exception {
        mockMvc.perform(post("/api/token-ml")
                .with(csrf())
                .param("grant", "authorization_code")
                .param("code", "test_code"))
                .andExpect(status().isOk());

        verify(saveTokenMLUseCase).execute("authorization_code", "test_code");
    }
}
