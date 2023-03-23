package com.canevim.integration.domain.identity.model.identity;

import com.canevim.integration.domain.identity.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "Identity")
@Table(name = "identity")
public class Identity extends BaseEntity {
    @Column(name = "identity_number")
    private String identityNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birth_year")
    private String birthYear;

    @Column(name = "result")
    private boolean result;
}