package com.order_service.orderServiceAplication.infrastructure.persistence.mongodb;

import com.order_service.orderServiceAplication.domain.model.OrderEvent;
import com.order_service.orderServiceAplication.domain.repository.IOrderEventRepository;
import com.order_service.orderServiceAplication.infrastructure.persistence.repository.IMongoOrderEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrderEventStoreAdapter implements IOrderEventRepository {
    private final IMongoOrderEventRepository mongoRepository;

    @Override
    public Mono<OrderEvent> save(OrderEvent event) {
        return mongoRepository.save(event);
    }

    @Override
    public Flux<OrderEvent> findByOrderId(String orderId) {
        return mongoRepository.findByOrderIdOrderByCreatedAtDesc(orderId);
    }
}
