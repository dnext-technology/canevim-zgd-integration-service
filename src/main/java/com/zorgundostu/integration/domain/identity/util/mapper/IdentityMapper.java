package com.zorgundostu.integration.domain.identity.util.mapper;

import com.zorgundostu.integration.domain.identity.model.identity.Identity;
import com.zorgundostu.integration.domain.identity.model.identity.IdentityDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface IdentityMapper {
    Identity toDao(IdentityDto dto);

    IdentityDto toDto(Identity identity);

}
