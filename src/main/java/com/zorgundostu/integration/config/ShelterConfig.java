package com.zorgundostu.integration.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "zgd.shelter", ignoreUnknownFields = false)
public class ShelterConfig {
    private String url;
    private String username;
    private String password;
    private String apikey;
}
