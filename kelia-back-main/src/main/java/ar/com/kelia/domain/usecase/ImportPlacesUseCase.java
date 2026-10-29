package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class ImportPlacesUseCase {

    @Autowired
    PlaceService service;

    public void execute(InputStream InputStream) throws Exception {
        service.parseAndSavePlaces(InputStream);
    }
}
