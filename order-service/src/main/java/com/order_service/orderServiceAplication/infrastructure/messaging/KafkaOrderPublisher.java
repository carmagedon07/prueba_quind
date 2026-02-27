package com.order_service.orderServiceAplication.infrastructure.messaging;

import com.order_service.orderServiceAplication.domain.model.Order;
import com.order_service.orderServiceAplication.domain.repository.IOrderEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class KafkaOrderPublisher implements IOrderEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaOrderPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Mono<Void> publishOrderCreated(Order order) {
        return Mono.fromRunnable(() ->
                kafkaTemplate.send("order-created-topic", String.valueOf(order.getId()), order)
        ).then();
    }

    @Override
    public Mono<Void> publishOrderCancelled(Order order) {
        return Mono.fromRunnable(() ->
                kafkaTemplate.send("order-events-topic", String.valueOf(order.getId()), order)
        ).then();
    }
}
