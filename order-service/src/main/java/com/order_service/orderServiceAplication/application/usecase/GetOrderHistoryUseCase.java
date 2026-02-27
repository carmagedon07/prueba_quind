package com.order_service.orderServiceAplication.application.usecase;

import com.order_service.orderServiceAplication.domain.model.OrderEvent;
import com.order_service.orderServiceAplication.domain.repository.IOrderEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GetOrderHistoryUseCase {

    private final IOrderEventRepository eventRepository;

    public Flux<OrderEvent> execute(String orderId) {
        // Retornamos el flujo de eventos ordenados por fecha
        return eventRepository.findByOrderId(orderId)
                .switchIfEmpty(Flux.empty()); // Evitamos nulls, devolvemos flujo vacío si no hay nada
    }
}
