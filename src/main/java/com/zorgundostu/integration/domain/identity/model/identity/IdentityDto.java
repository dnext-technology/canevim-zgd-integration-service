package com.zorgundostu.integration.domain.identity.model.identity;

public record IdentityDto(
        String identityNumber,
        String name,
        String surname,
        String birthYear,
        boolean result) {

}
