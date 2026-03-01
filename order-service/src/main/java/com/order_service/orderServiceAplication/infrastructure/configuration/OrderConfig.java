package com.order_service.orderServiceAplication.infrastructure.configuration;

<<<<<<< HEAD
=======
import com.order_service.orderServiceAplication.application.usecase.CreateOrderUseCase;
import com.order_service.orderServiceAplication.domain.repository.IOrderRepository;
import com.order_service.orderServiceAplication.domain.repository.IOrderEventPublisher;
import org.springframework.context.annotation.Bean;
>>>>>>> develop_checkout_2
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {
<<<<<<< HEAD
    // Se eliminó el @Bean factory para CreateOrderUseCase porque la clase
    // CreateOrderUseCase ya está anotada con @Service y Spring la detectará automáticamente.
=======

    @Bean
    public CreateOrderUseCase createOrderUseCase(IOrderRepository orderRepository,
                                                 IOrderEventPublisher orderEventPublisher) {
        return new CreateOrderUseCase(orderRepository, orderEventPublisher);
    }

>>>>>>> develop_checkout_2
}
