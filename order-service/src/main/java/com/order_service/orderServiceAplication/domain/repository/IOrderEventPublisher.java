package com.order_service.orderServiceAplication.domain.repository;

import com.order_service.orderServiceAplication.domain.model.Order;
import reactor.core.publisher.Mono;

public interface IOrderEventPublisher {
    Mono<Void> publishOrderCreatedEvent(Order event);
}
