package com.canevim.integration.domain.shelter.service;

import java.util.HashMap;
import java.util.Map;

import com.canevim.integration.client.IntegrationHttpClient;
import com.canevim.integration.client.IntegrationHttpUtils;
import com.canevim.integration.config.ShelterConfig;
import com.canevim.integration.domain.shelter.model.ShelterDto;
import com.canevim.integration.domain.shelter.model.ShelterMinistryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShelterService {
    private final ShelterConfig shelterConfig;
    private final IntegrationHttpClient integrationHttpClient;

    public ShelterService(ShelterConfig shelterConfig, IntegrationHttpClient integrationHttpClient){
        this.shelterConfig = shelterConfig;
        this.integrationHttpClient = integrationHttpClient;
    }

    public ShelterMinistryResponse sendShelterOfferToTheMinistry(ShelterDto shelterDto){
        var header = IntegrationHttpUtils.generateMinistryCallHeader(shelterConfig.getUsername(), shelterConfig.getPassword(), shelterConfig.getApikey());
        var response = integrationHttpClient.doPost(shelterConfig.getUrl(), header, shelterDto, ShelterMinistryResponse.class);
        log.info("Ministry Response :: {}", response);
        return response;
    }
}
