package com.order_service.orderServiceAplication.infrastructure.persistence.repository;

import com.order_service.orderServiceAplication.domain.model.OrderEvent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface IMongoOrderEventRepository extends ReactiveMongoRepository<OrderEvent, String> {

    Flux<OrderEvent> findByOrderIdOrderByCreatedAtDesc(String orderId);
}
