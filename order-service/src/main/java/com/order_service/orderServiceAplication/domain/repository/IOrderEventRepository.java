package com.order_service.orderServiceAplication.domain.repository;

import com.order_service.orderServiceAplication.domain.model.OrderEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IOrderEventRepository {
    Mono<OrderEvent> save(OrderEvent event);
    Flux<OrderEvent> findByOrderId(String orderId);
}
