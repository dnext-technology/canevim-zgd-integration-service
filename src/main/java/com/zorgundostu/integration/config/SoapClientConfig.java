package com.zorgundostu.integration.config;


import com.zorgundostu.integration.domain.identity.client.IdentityClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;


@Data
@Configuration
@ConfigurationProperties(prefix = "zgd.identity", ignoreUnknownFields = false)
public class SoapClientConfig {
    public String url;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.zorgundostu.integration.config.gen");
//        marshaller.setPackagesToScan("com.zorgundostu.integration.config.gen");
        return marshaller;
    }
    @Bean
    public IdentityClient countryClient(Jaxb2Marshaller marshaller) {
        IdentityClient client = new IdentityClient();
        client.setDefaultUri(url);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
