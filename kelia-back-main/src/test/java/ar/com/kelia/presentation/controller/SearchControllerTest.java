package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.model.Product;
import ar.com.kelia.domain.usecase.GetRandomProductsUseCase;
import ar.com.kelia.domain.usecase.SearchProductUseCase;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@TestPropertySource(properties = {
        "spring.security.user.name=test",
        "spring.security.user.password=test"
})
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchProductUseCase searchProductUseCase;

    @MockBean
    private GetRandomProductsUseCase getRandomProductsUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testSearchProductWithTerm() throws Exception {
        List<Product> products = List.of(new Product());

        Mockito.when(searchProductUseCase.execute(anyString()))
                .thenReturn(products);

        mockMvc.perform(get("/api/product")
                .param("term", "harina"))
                .andExpect(status().isOk());

        verify(searchProductUseCase).execute("harina");
    }

    @Test
    @WithMockUser
    void testSearchProductWithoutTerm() throws Exception {
        List<Product> products = List.of(new Product());

        Mockito.when(getRandomProductsUseCase.execute())
                .thenReturn(products);

        mockMvc.perform(get("/api/product"))
                .andExpect(status().isOk());

        verify(getRandomProductsUseCase).execute();
    }
}
