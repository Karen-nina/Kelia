package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.mongo.Place;
import ar.com.kelia.domain.repository.PlaceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaceServiceTest {

    private PlaceRepository placeRepository;
    private PlaceService placeService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        placeRepository = mock(PlaceRepository.class);
        placeService = new PlaceService(placeRepository, objectMapper);
    }

    @Test
    void testFindByNameContainingIgnoreCase() {
        List<Place> mockList = List.of(new Place());
        when(placeRepository.findByNameContainingIgnoreCase("gimnasio")).thenReturn(mockList);

        List<Place> result = placeService.findByNameContainingIgnoreCase("gimnasio");
        assertEquals(mockList, result);
        verify(placeRepository).findByNameContainingIgnoreCase("gimnasio");
    }

    @Test
    void testParseAndSavePlaces() throws Exception {

    }
}
