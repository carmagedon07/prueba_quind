package com.order_service.orderServiceAplication.infrastructure.rest;

import com.order_service.orderServiceAplication.application.dto.OrderRequest;
import com.order_service.orderServiceAplication.application.dto.OrderResponse;
import com.order_service.orderServiceAplication.application.usecase.CreateOrderUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    public Mono<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        return createOrderUseCase.execute(orderRequest)
                .map(order -> new OrderResponse(
                        order.getId(),
                        order.getCustomerId(),
                        order.getTotalAmount(),
                        (order.getStatus() != null ? order.getStatus().name() : null)));
    }
}
