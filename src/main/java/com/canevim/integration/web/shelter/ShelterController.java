package com.canevim.integration.web.shelter;


import java.util.Map;

import com.canevim.integration.domain.shelter.model.ShelterDto;
import com.canevim.integration.domain.shelter.service.ShelterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class ShelterController implements ShelterApi{

    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService){
        this.shelterService = shelterService;
    }

    @Override
    public ResponseEntity<Object> sendShelterOfferToTheMinistry(Map<String, String> header, ShelterDto shelterDto) {
        return ResponseEntity.ok(shelterService.sendShelterOfferToTheMinistry(shelterDto));
    }
}
