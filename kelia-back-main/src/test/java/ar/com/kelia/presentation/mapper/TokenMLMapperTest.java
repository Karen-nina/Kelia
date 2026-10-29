package ar.com.kelia.presentation.mapper;

import ar.com.kelia.domain.model.TokenML;
import ar.com.kelia.infrastructure.dto.TokenMLDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Test para TokenMLMapper: cubre dtoToEntity y entityToDto usando la instancia generada por MapStruct
class TokenMLMapperTest {
    @Test
    void testDtoToEntityAndBack() {
        // Crear DTO de ejemplo
        TokenMLDTO dto = new TokenMLDTO();
        dto.setAccessToken("token");
        dto.setTokenType("Bearer");
        dto.setExpiresIn(3600);
        dto.setRefreshToken("refresh");
        // Mapear a entidad
        TokenML entity = TokenMLMapper.INSTANCE.dtoToEntity(dto);
        assertNotNull(entity);
        assertEquals("token", entity.getAccessToken());
        assertEquals("Bearer", entity.getTokenType());
        assertEquals(3600, entity.getExpiresIn());
        assertEquals("refresh", entity.getRefreshToken());
        // Mapear de vuelta a DTO
        TokenMLDTO dto2 = TokenMLMapper.INSTANCE.entityToDto(entity);
        assertNotNull(dto2);
        assertEquals("token", dto2.getAccessToken());
        assertEquals("Bearer", dto2.getTokenType());
        assertEquals(3600, dto2.getExpiresIn());
        assertEquals("refresh", dto2.getRefreshToken());
    }

    @Test
    void testDtoToEntityAndBackWithNulls() {
        // Caso: DTO y entidad con campos nulos
        TokenMLDTO dto = new TokenMLDTO();
        // Todos los campos nulos
        TokenML entity = TokenMLMapper.INSTANCE.dtoToEntity(dto);
        assertNotNull(entity);
        assertNull(entity.getAccessToken());
        assertNull(entity.getTokenType());
        assertEquals(0, entity.getExpiresIn());
        assertNull(entity.getRefreshToken());
        // Mapear de vuelta a DTO
        TokenMLDTO dto2 = TokenMLMapper.INSTANCE.entityToDto(entity);
        assertNotNull(dto2);
        assertNull(dto2.getAccessToken());
        assertNull(dto2.getTokenType());
        assertEquals(0, dto2.getExpiresIn());
        assertNull(dto2.getRefreshToken());
    }
}