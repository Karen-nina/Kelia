package ar.com.kelia.presentation.controller;

import ar.com.kelia.domain.model.mongo.Place;
import ar.com.kelia.domain.model.PlaceDetail;
import ar.com.kelia.domain.model.mongo.Post;
import ar.com.kelia.domain.service.PostService;
import ar.com.kelia.domain.usecase.GetPlaceDetailsUseCase;
import ar.com.kelia.domain.usecase.GetPlacesFromTermUseCase;
import ar.com.kelia.domain.usecase.ImportPlacesUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlaceController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImportPlacesUseCase importPlacesUseCase;

    @MockBean
    private GetPlacesFromTermUseCase getPlacesFromTermUseCase;

    @MockBean
    private GetPlaceDetailsUseCase getPlaceDetailsUseCase;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetPlacesAndPostsByName() throws Exception {
        Place place = new Place();
        place.setId("p1");
        place.setName("Cafe");
        place.setCategory("Food");

        Post post = new Post();
        post.setId("post1");
        post.setContent("Great cafe!");

        when(getPlacesFromTermUseCase.execute("Cafe")).thenReturn(List.of(place));
        when(postService.getPostFromFilter("Cafe")).thenReturn(List.of(post));

        mockMvc.perform(get("/api/places")
                        .param("name", "Cafe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.places[0].id").value("p1"))
                .andExpect(jsonPath("$.places[0].name").value("Cafe"))
                .andExpect(jsonPath("$.places[0].category").value("Food"))
                .andExpect(jsonPath("$.posts[0].id").value("post1"))
                .andExpect(jsonPath("$.posts[0].content").value("Great cafe!"));

        verify(getPlacesFromTermUseCase).execute("Cafe");
        verify(postService).getPostFromFilter("Cafe");
    }

    @Test
    void testGetPlaceDetails() throws Exception {
        PlaceDetail detail = new PlaceDetail();
        detail.setName("Cafe Central");
        detail.setAddress("123 Main St");
        detail.setPhoneNumber("+123456789");
        detail.setWebsite("https://cafe.com");
        detail.setRating(4.5);
        detail.setTotalUserRatings(100);
        detail.setLatitude(-34.6037);
        detail.setLongitude(-58.3816);
        detail.setOpeningHours(List.of("Mon-Fri 8-18", "Sat 9-14"));
        detail.setReviews(List.of()); // vacías para simplificar

        when(getPlaceDetailsUseCase.execute("p1")).thenReturn(detail);

        mockMvc.perform(get("/api/places/p1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cafe Central"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.phoneNumber").value("+123456789"))
                .andExpect(jsonPath("$.website").value("https://cafe.com"))
                .andExpect(jsonPath("$.rating").value(4.5))
                .andExpect(jsonPath("$.totalUserRatings").value(100))
                .andExpect(jsonPath("$.latitude").value(-34.6037))
                .andExpect(jsonPath("$.longitude").value(-58.3816))
                .andExpect(jsonPath("$.openingHours[0]").value("Mon-Fri 8-18"))
                .andExpect(jsonPath("$.openingHours[1]").value("Sat 9-14"));

        verify(getPlaceDetailsUseCase).execute("p1");
    }

    @Test
    void testUploadJsonFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "places.json", "application/json", "[{}]".getBytes());

        doNothing().when(importPlacesUseCase).execute(any());

        mockMvc.perform(multipart("/api/places").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("File processed successfully"));

        verify(importPlacesUseCase).execute(any());
    }

    @Test
    void testUploadInvalidFileType() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "places.txt", "text/plain", "some data".getBytes());

        mockMvc.perform(multipart("/api/places").file(file))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid file type. Please upload a .json file."));
    }
}
