package com.order_service.orderServiceAplication.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {
    // Se eliminó el @Bean factory para CreateOrderUseCase porque la clase
    // CreateOrderUseCase ya está anotada con @Service y Spring la detectará automáticamente.
}
