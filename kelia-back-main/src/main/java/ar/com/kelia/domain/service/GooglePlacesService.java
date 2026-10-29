package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.PlaceDetail;

public interface GooglePlacesService {
    PlaceDetail getPlaceDetails(String placeId);

}
