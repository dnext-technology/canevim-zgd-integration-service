package com.zorgundostu.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ZgdIntegrationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZgdIntegrationServiceApplication.class, args);
		log.debug("http://localhost:8082/api/zgd-integration/v1/swagger-ui/index.html");
	}

}
