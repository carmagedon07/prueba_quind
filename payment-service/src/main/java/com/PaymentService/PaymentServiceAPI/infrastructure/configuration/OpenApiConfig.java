package com.PaymentService.PaymentServiceAPI.infrastructure.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("payment-service-api")     // Grupo específico para pagos
                .pathsToMatch("/**")
                .displayName("Payment Service API")  // Título en Swagger UI
                .build();
    }

}
