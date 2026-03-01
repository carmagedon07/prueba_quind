package com.order_service.orderServiceAplication.infrastructure.messaging;

import com.order_service.orderServiceAplication.domain.model.Order;
import com.order_service.orderServiceAplication.domain.repository.IOrderEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class KafkaOrderPublisher implements IOrderEventPublisher {

<<<<<<< HEAD
    private final KafkaTemplate <String, Object> kafkaTemplate;
=======
    private final KafkaTemplate<String, Object> kafkaTemplate;
>>>>>>> develop_checkout_2

    public KafkaOrderPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
<<<<<<< HEAD
    public Mono<Void> publishOrderCreatedEvent(Order event) {
        return Mono.fromRunnable(
                () -> kafkaTemplate.send("order-created-topic",event.getId(),event)
=======
    public Mono<Void> publishOrderCreated(Order order) {
        return Mono.fromRunnable(() ->
                kafkaTemplate.send("order-created-topic", String.valueOf(order.getId()), order)
        ).then();
    }

    @Override
    public Mono<Void> publishOrderCancelled(Order order) {
        return Mono.fromRunnable(() ->
                kafkaTemplate.send("order-events-topic", String.valueOf(order.getId()), order)
>>>>>>> develop_checkout_2
        ).then();
    }
}
