package com.order_service.orderServiceAplication.domain.repository;

import com.order_service.orderServiceAplication.domain.model.Order;
<<<<<<< HEAD
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface IOrderRepository{
    Mono<Order> save(Order order);
    Mono<Order> findById(Long id);
    Flux<Order> findByCustomerId(String customerId, int page, int size);
    Flux<Order> findAll();
    Mono<Void> deleteById(Long id);
=======
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface IOrderRepository {
    Mono<Order> save(Order order);
    Mono<Order> findById(String id);
    Flux<Order> findByCustomerId(String customerId, Pageable pageable);
>>>>>>> develop_checkout_2
}
