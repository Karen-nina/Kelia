package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.model.PlaceDetail;
import ar.com.kelia.domain.service.GooglePlacesService;
import org.springframework.stereotype.Component;

@Component
public class GetPlaceDetailsUseCase {

    private final GooglePlacesService googlePlacesProvider;

    public GetPlaceDetailsUseCase(GooglePlacesService provider) {
        this.googlePlacesProvider = provider;
    }

    public PlaceDetail execute(String placeId) {
        return googlePlacesProvider.getPlaceDetails(placeId);
    }
}


