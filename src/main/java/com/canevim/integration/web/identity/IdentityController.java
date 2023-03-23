package com.canevim.integration.web.identity;

import com.canevim.integration.domain.identity.model.identity.IdentityDto;
import com.canevim.integration.domain.identity.service.IdentityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class IdentityController implements IdentityApi {

    private final IdentityService identityService;


    @Override
    public ResponseEntity<Object> identityNumberVerification(Map<String, String> header, IdentityDto dto) {
        return ResponseEntity.ok(identityService.verifyIdentityNumber(dto));
    }

    @Override
    public ResponseEntity<Object> getAllIdentities(Map<String, String> header, Integer page, Integer size) {
        return ResponseEntity.ok(identityService.getAllIdentities(page, size));
    }
}
