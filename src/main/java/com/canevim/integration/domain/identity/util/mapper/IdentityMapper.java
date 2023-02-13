package com.canevim.integration.domain.identity.util.mapper;

import com.canevim.integration.domain.identity.model.identity.Identity;
import com.canevim.integration.domain.identity.model.identity.IdentityDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface IdentityMapper {
    Identity toDao(IdentityDto dto);

    IdentityDto toDto(Identity identity);

}
