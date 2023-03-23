package com.canevim.integration.domain.identity.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Header {

    private String errorCode;
    private String description;
    private String message;
}
