package com.canevim.integration.domain.identity.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseAndHeader<T> {

    T body;

    Header header;
}
