package com.zorgundostu.integration.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "zgd.sms", ignoreUnknownFields = false)
public class SmsConfig {
    public String username;
    public String password;
    public String vendor;
    public String header;
    public String charset;
}
