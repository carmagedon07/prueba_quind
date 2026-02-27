package com.order_service.orderServiceAplication.infrastructure.configuration;

import com.order_service.orderServiceAplication.application.usecase.CreateOrderUseCase;
import com.order_service.orderServiceAplication.domain.repository.IOrderRepository;
import com.order_service.orderServiceAplication.domain.repository.IOrderEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    @Bean
    public CreateOrderUseCase createOrderUseCase(IOrderRepository orderRepository,
                                                 IOrderEventPublisher orderEventPublisher) {
        return new CreateOrderUseCase(orderRepository, orderEventPublisher);
    }

}
