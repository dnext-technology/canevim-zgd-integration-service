package com.canevim.integration.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "zgd.identity", ignoreUnknownFields = false)
public class SoapClientConfig {
    private String url;
}
