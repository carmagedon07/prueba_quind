package com.order_service.orderServiceAplication.domain.repository;

import com.order_service.orderServiceAplication.domain.model.Order;
import reactor.core.publisher.Mono;

public interface IOrderEventPublisher {
<<<<<<< HEAD
    Mono<Void> publishOrderCreatedEvent(Order event);
=======
    Mono<Void> publishOrderCreated(Order order);

    Mono<Void> publishOrderCancelled(Order order);
>>>>>>> develop_checkout_2
}
