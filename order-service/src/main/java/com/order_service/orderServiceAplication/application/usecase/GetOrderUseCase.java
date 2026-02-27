package com.order_service.orderServiceAplication.application.usecase;

import com.order_service.orderServiceAplication.domain.model.Order;
import com.order_service.orderServiceAplication.domain.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetOrderUseCase {

    private final IOrderRepository  repository;

    public Mono<Order> findById(String id) {
        return repository.findById(id);
    }

    public Flux<Order> findByCustomer(String customerId, int page, int size) {
        return repository.findByCustomerId(customerId, PageRequest.of(page, size));
    }

}
