package ar.com.kelia.infrastructure.adapter;

import ar.com.kelia.domain.repository.ProductExcelRepository;
import ar.com.kelia.dto.ProductExcelDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelProductRepository implements ProductExcelRepository {
    @Override
    public List<ProductExcelDTO> parse(MultipartFile file) {
        try {
            List<ProductExcelDTO> products = new ArrayList<>();
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            if (rows.hasNext()) {
                rows.next();
            }

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                ProductExcelDTO product = new ProductExcelDTO();

                product.setBrand(truncate(currentRow.getCell(2).getStringCellValue()));
                product.setTradeName(truncate(currentRow.getCell(3).getStringCellValue()));
                product.setSalesName(truncate(currentRow.getCell(4).getStringCellValue()));
                product.setProductType(truncate(currentRow.getCell(5).getStringCellValue()));
                product.setStatus(truncate(currentRow.getCell(6).getStringCellValue()));

                products.add(product);
            }

            workbook.close();
            return products;
        } catch (IOException e) {
            throw new RuntimeException("Error processing Excel file: " + e.getMessage());
        }
    }

    private String truncate(String value) {
        return value != null && value.length() > 100 ? value.substring(0, 100) : value;
    }
}
