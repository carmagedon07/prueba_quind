package com.order_service.orderServiceAplication.infrastructure.rest;

import com.order_service.orderServiceAplication.application.dto.OrderRequest;
import com.order_service.orderServiceAplication.application.dto.OrderResponse;
import com.order_service.orderServiceAplication.application.usecase.CreateOrderUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        return createOrderUseCase.execute(request)
                .map(order -> new OrderResponse(
                        order.getId(),
                        order.getCustomerId(),
                        order.getTotalAmount(),
                        order.getStatus().name()
                ));
    }

}
