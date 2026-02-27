package com.order_service.orderServiceAplication.domain.repository;

import com.order_service.orderServiceAplication.domain.model.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface IOrderRepository {
    Mono<Order> save(Order order);
    Mono<Order> findById(String id);
    Flux<Order> findByCustomerId(String customerId, Pageable pageable);
}
