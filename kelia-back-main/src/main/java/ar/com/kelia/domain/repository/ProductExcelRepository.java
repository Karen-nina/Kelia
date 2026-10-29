package ar.com.kelia.domain.repository;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductExcelRepository {
    List<ar.com.kelia.dto.ProductExcelDTO> parse(MultipartFile file);
}
