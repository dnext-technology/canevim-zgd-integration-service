package com.canevim.integration.client;

import java.util.Map;

public class IntegrationHttpUtils {

    private IntegrationHttpUtils() {}

    public static Map<String, String> generateMinistryCallHeader(String username, String password, String apiKey) {
        return Map.of("Content-Type", "application/json", "username", username, "password", password, "x-api-key", apiKey);
    }
}
