package com.canevim.integration.domain.notification.client;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.canevim.integration.config.SmsConfig;

import java.util.List;

@Slf4j
@Service
public class SmsClient {

    private final SmsConfig smsConfig;
    private final RestTemplate restTemplate;

    public SmsClient(SmsConfig smsConfig, RestTemplateBuilder restTemplateBuilder) {
        this.smsConfig = smsConfig;
        this.restTemplate = restTemplateBuilder.build();
    }

    public String sendSms(@NotNull List<String> gsmList, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", smsConfig.username);
        map.add("password", smsConfig.password);
        map.add("vendor", smsConfig.vendor);
        map.add("header", smsConfig.header);
        map.add("message", message);
        for (String gsm : gsmList) {
            map.add("gsm", gsm);
        }
        map.add("charset", smsConfig.charset);
        map.add("startdate", "");
        map.add("exdate", "");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity(smsConfig.url, request, String.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return "SMS sent successfully, response: " + response.getBody();
        } else {
            log.error("SMS could not be sent, response: {}", response);
            return "SMS could not be sent";
        }
    }
}
