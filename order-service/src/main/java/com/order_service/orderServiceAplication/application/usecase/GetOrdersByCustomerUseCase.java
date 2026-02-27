package com.order_service.orderServiceAplication.application.usecase;

import com.order_service.orderServiceAplication.domain.model.Order;
import com.order_service.orderServiceAplication.domain.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GetOrdersByCustomerUseCase {
    private final IOrderRepository repository;
    public Flux<Order> execute(String customerId, int page, int size) {

        // para asegurar que el usuario que consulta tiene permiso para ver ese customerId.
        Flux<Order> result = repository.findByCustomerId(customerId, PageRequest.of(page, size));

        return result != null ? result : Flux.empty();
    }
}
