package com.order_service.orderServiceAplication.application.usecase;

import com.order_service.orderServiceAplication.domain.exception.OrderNotFoundException;
import com.order_service.orderServiceAplication.domain.model.Order;
import com.order_service.orderServiceAplication.domain.model.OrderEvent;
import com.order_service.orderServiceAplication.domain.model.OrderStatus;
import com.order_service.orderServiceAplication.domain.repository.IOrderEventPublisher;
import com.order_service.orderServiceAplication.domain.repository.IOrderEventRepository;
import com.order_service.orderServiceAplication.domain.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CancelOrderUseCase {

    private final IOrderRepository  repository;
    private final IOrderEventPublisher kafkaProducer;
    private final IOrderEventRepository eventStore;


    public Mono<Order> execute(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new OrderNotFoundException(id)))
                .flatMap(order -> {
                    if (!order.canBeCancelled()) {
                        return Mono.error(new IllegalStateException("No se puede cancelar en estado: " + order.getStatus()));
                    }

                    order.setStatus(OrderStatus.CANCELLED);

                    // 1. Guardamos el estado actual
                    return repository.save(order)
                            .flatMap(savedOrder -> {
                                // 2. Creamos el evento para el historial (Event Sourcing)
                                OrderEvent historyEvent = OrderEvent.builder()
                                        .orderId(String.valueOf(savedOrder.getId()))
                                        .eventType("ORDER_CANCELLED")
                                        .description("El cliente solicitó la cancelación de la orden")
                                        .createdAt(LocalDateTime.now())
                                        .build();

                                // 3. Guardamos en Mongo y disparamos a Kafka en paralelo
                                return eventStore.save(historyEvent)
                                        .then(kafkaProducer.publishOrderCancelled(savedOrder))
                                        .thenReturn(savedOrder);
                            });
                });
    }

}



