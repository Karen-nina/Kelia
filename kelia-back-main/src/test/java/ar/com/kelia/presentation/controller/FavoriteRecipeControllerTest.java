package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.usecase.FavoriteRecipeUseCase;
import ar.com.kelia.domain.usecase.GetFavoriteRecipeByUidUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FavoriteRecipeController.class)
@TestPropertySource(properties = {
        "spring.security.enabled=false"
})
class FavoriteRecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoriteRecipeUseCase favoriteRecipeUseCase;

    @MockBean
    private GetFavoriteRecipeByUidUseCase getFavoriteRecipeByUidUseCase;

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
    void testFavoriteRecipe() throws Exception {
        mockMvc.perform(post("/api/favorite")
                .with(csrf())
                .param("recipeId", "r789")
                .param("uid", "abc123"))
                .andExpect(status().isCreated());

        verify(favoriteRecipeUseCase).execute("r789", "abc123");
    }

    @Test
    @WithMockUser
    void testGetFavorites() throws Exception {
        mockMvc.perform(get("/api/favorite")
                .param("uid", "abc123"))
                .andExpect(status().isOk());

        verify(getFavoriteRecipeByUidUseCase).execute("abc123");
    }
}
