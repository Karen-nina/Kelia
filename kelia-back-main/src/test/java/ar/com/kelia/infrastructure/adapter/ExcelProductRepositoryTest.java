package ar.com.kelia.infrastructure.adapter;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ExcelProductRepositoryTest {

    private final ExcelProductRepository repository = new ExcelProductRepository();

    @Test
    void shouldParseExcelFileAndReturnProductExcelDTOs() throws Exception {
        // Crear workbook en memoria
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");

        // Crear header (fila 0)
        Row header = sheet.createRow(0);
        header.createCell(2).setCellValue("Brand");
        header.createCell(3).setCellValue("TradeName");
        header.createCell(4).setCellValue("SalesName");
        header.createCell(5).setCellValue("ProductType");
        header.createCell(6).setCellValue("Status");

        // Crear fila 1 con datos
        Row row1 = sheet.createRow(1);
        row1.createCell(2).setCellValue("BrandNameExample");
        row1.createCell(3).setCellValue("TradeNameExample");
        row1.createCell(4).setCellValue("SalesNameExample");
        row1.createCell(5).setCellValue("ProductTypeExample");
        row1.createCell(6).setCellValue("StatusExample");

        // Crear fila 2 con datos con strings largos para probar truncate
        String longString = "a".repeat(150);
        Row row2 = sheet.createRow(2);
        row2.createCell(2).setCellValue(longString);
        row2.createCell(3).setCellValue(longString);
        row2.createCell(4).setCellValue(longString);
        row2.createCell(5).setCellValue(longString);
        row2.createCell(6).setCellValue(longString);

        // Volcar workbook a un byte array para simular archivo upload
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "test.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                baos.toByteArray()
        );

        // Ejecutar método a testear
        List<ar.com.kelia.dto.ProductExcelDTO> products = repository.parse(multipartFile);

        // Validaciones
        assertThat(products).hasSize(2);

        ar.com.kelia.dto.ProductExcelDTO first = products.get(0);
        assertThat(first.getBrand()).isEqualTo("BrandNameExample");
        assertThat(first.getTradeName()).isEqualTo("TradeNameExample");
        assertThat(first.getSalesName()).isEqualTo("SalesNameExample");
        assertThat(first.getProductType()).isEqualTo("ProductTypeExample");
        assertThat(first.getStatus()).isEqualTo("StatusExample");

        ar.com.kelia.dto.ProductExcelDTO second = products.get(1);
        // El truncate limita a 100 chars
        assertThat(second.getBrand()).hasSize(100);
        assertThat(second.getTradeName()).hasSize(100);
        assertThat(second.getSalesName()).hasSize(100);
        assertThat(second.getProductType()).hasSize(100);
        assertThat(second.getStatus()).hasSize(100);
    }
}
