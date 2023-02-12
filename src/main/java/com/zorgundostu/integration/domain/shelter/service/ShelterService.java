package com.zorgundostu.integration.domain.shelter.service;

import java.util.HashMap;
import java.util.Map;

import com.zorgundostu.integration.client.ShelterHttpClient;
import com.zorgundostu.integration.domain.identity.model.Header;
import com.zorgundostu.integration.domain.shelter.model.ShelterDto;
import com.zorgundostu.integration.domain.shelter.model.ShelterMinistryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShelterService {
    private final ShelterHttpClient shelterHttpClient;

    public ShelterService(ShelterHttpClient shelterHttpClient){
        this.shelterHttpClient = shelterHttpClient;
    }

    public ShelterMinistryResponse sendShelterOfferToTheMinistry(Map<String, String> header, ShelterDto shelterDto){
        String url = "https://servisoagtest.icisleri.gov.tr/Services/Producer/EsbApi/GonulluKonutServisi/GonulluKonutKaydet";
        Map<String, String> headers = new HashMap<>();
        headers.put("username", "0978002295908001");
        headers.put("password", "b47dfdd2d981d0575655b28360c85539");
        headers.put("x-api-key", "77E54B8B-F916-4D5D-A3E8-8959E08DFB5C");
        var response = shelterHttpClient.doPost(url, headers, shelterDto, ShelterMinistryResponse.class);
        return response;
    }
}
