package ar.com.kelia.presentation.mapper;

import ar.com.kelia.infrastructure.dto.TokenMLDTO;
import ar.com.kelia.domain.model.TokenML;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TokenMLMapper {

    TokenMLMapper INSTANCE = Mappers.getMapper(TokenMLMapper.class);

    @Mapping(source = "accessToken", target = "accessToken")
    @Mapping(source = "tokenType", target = "tokenType")
    @Mapping(source = "expiresIn", target = "expiresIn")
    @Mapping(source = "refreshToken", target = "refreshToken")
    TokenML dtoToEntity(TokenMLDTO dto);

    @Mapping(source = "accessToken", target = "accessToken")
    @Mapping(source = "tokenType", target = "tokenType")
    @Mapping(source = "expiresIn", target = "expiresIn")
    @Mapping(source = "refreshToken", target = "refreshToken")
    TokenMLDTO entityToDto(TokenML entity);
}
