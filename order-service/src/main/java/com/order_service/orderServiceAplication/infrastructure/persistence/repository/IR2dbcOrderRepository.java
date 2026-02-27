package com.order_service.orderServiceAplication.infrastructure.persistence.repository;

import com.order_service.orderServiceAplication.infrastructure.persistence.entity.OrderEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IR2dbcOrderRepository  extends ReactiveCrudRepository<OrderEntity, Long> {
}
