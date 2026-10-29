package ar.com.kelia.infrastructure.dto;

import ar.com.kelia.domain.model.mongo.Place;
import ar.com.kelia.domain.model.mongo.Post;

import java.util.List;

public class SiteDTO {
    public List<Place> sites;
    public List<Post> posts;
}
