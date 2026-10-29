package ar.com.kelia.domain.service;

import ar.com.kelia.domain.model.ProductANMAT;
import ar.com.kelia.domain.repository.ProductANMATRepository;
import ar.com.kelia.domain.repository.ProductExcelRepository;
import ar.com.kelia.dto.ProductExcelDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.mockito.Mockito.*;

class ProductANMATFromExcelServiceTest {

    private ProductANMATRepository productRepository;
    private ProductExcelRepository excelRepository;
    private ProductANMATFromExcelService service;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductANMATRepository.class);
        excelRepository = mock(ProductExcelRepository.class);
        service = new ProductANMATFromExcelService(productRepository, excelRepository);
    }

    @Test
    void testImportProductFromExcel() {
        MultipartFile mockFile = mock(MultipartFile.class);

        ProductExcelDTO dto = new ProductExcelDTO();
        dto.setBrand("MarcaX");
        dto.setTradeName("NombreComercial");
        dto.setSalesName("NombreVenta");
        dto.setProductType("Tipo");
        dto.setStatus("VIGENTE");

        when(excelRepository.parse(mockFile)).thenReturn(List.of(dto));

        service.importProductFromExcel(mockFile);

        verify(productRepository).deleteAll();
        verify(excelRepository).parse(mockFile);
        verify(productRepository).saveAll(anyList());
    }
}
