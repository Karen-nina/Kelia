package ar.com.kelia.domain.usecase;

import ar.com.kelia.domain.service.ProductANMATFromExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImportProductFromExcelUseCase {

    private final ProductANMATFromExcelService service;

    public ImportProductFromExcelUseCase(ProductANMATFromExcelService service) {
        this.service = service;
    }


    public void execute(MultipartFile file) {
        service.importProductFromExcel(file);
    }
}