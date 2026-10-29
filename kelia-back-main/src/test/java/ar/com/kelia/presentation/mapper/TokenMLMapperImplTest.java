package ar.com.kelia.presentation.mapper;

import ar.com.kelia.domain.model.TokenML;
import ar.com.kelia.infrastructure.dto.TokenMLDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas para la implementación del mapper de tokens de MercadoLibre.
 * Prueba la funcionalidad de mapeo entre DTOs y entidades de tokens.
 */
class TokenMLMapperImplTest {
    private final TokenMLMapper mapper = TokenMLMapper.INSTANCE;

    /**
     * Prueba que el método dtoToEntity mapee correctamente los campos relevantes.
     * Verifica que se mapeen correctamente los campos del DTO a la entidad.
     */
    @Test
    void dtoToEntity_mapsRelevantFields() {
        // Arrange - Preparar datos de prueba
        TokenMLDTO dto = new TokenMLDTO();
        dto.setAccessToken("access");
        dto.setTokenType("Bearer");
        dto.setExpiresIn(3600);
        dto.setRefreshToken("refresh");
        // scope y userId no se mapean

        // Act - Ejecutar el método a probar
        TokenML entity = mapper.dtoToEntity(dto);

        // Assert - Verificar resultados esperados
        assertNotNull(entity);
        assertEquals("access", entity.getAccessToken());
        assertEquals("Bearer", entity.getTokenType());
        assertEquals(3600, entity.getExpiresIn());
        assertEquals("refresh", entity.getRefreshToken());
    }

    /**
     * Prueba que el método entityToDto mapee correctamente los campos relevantes.
     * Verifica que se mapeen correctamente los campos de la entidad al DTO.
     */
    @Test
    void entityToDto_mapsRelevantFields() {
        // Arrange - Preparar datos de prueba
        TokenML entity = new TokenML();
        entity.setAccessToken("access");
        entity.setTokenType("Bearer");
        entity.setExpiresIn(3600);
        entity.setRefreshToken("refresh");
        // id, createdAt, expiresAt no se mapean

        // Act - Ejecutar el método a probar
        TokenMLDTO dto = mapper.entityToDto(entity);

        // Assert - Verificar resultados esperados
        assertNotNull(dto);
        assertEquals("access", dto.getAccessToken());
        assertEquals("Bearer", dto.getTokenType());
        assertEquals(3600, dto.getExpiresIn());
        assertEquals("refresh", dto.getRefreshToken());
    }
}