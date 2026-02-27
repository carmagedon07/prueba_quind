package com.order_service.orderServiceAplication.infrastructure.persistence.repository;

import com.order_service.orderServiceAplication.infrastructure.persistence.entity.OrderEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.awt.print.Pageable;

@Repository
public interface IR2dbcOrderRepository  extends ReactiveCrudRepository<OrderEntity, Long> {
    Flux<OrderEntity> findByCustomerId(String customerId, Pageable pageable);

}
