package com.order_service.orderServiceAplication.infrastructure.rest;

import com.order_service.orderServiceAplication.application.dto.OrderRequest;
import com.order_service.orderServiceAplication.application.dto.OrderResponse;
import com.order_service.orderServiceAplication.application.dto.OrderResponseDTO;
import com.order_service.orderServiceAplication.application.mapper.OrderMapper;
import com.order_service.orderServiceAplication.application.usecase.CreateOrderUseCase;
import com.order_service.orderServiceAplication.application.usecase.GetOrderHistoryUseCase;
import com.order_service.orderServiceAplication.application.usecase.GetOrderUseCase;
import com.order_service.orderServiceAplication.application.usecase.GetOrdersByCustomerUseCase;
import com.order_service.orderServiceAplication.domain.model.OrderEvent;
import com.order_service.orderServiceAplication.domain.repository.IOrderEventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final IOrderEventRepository eventRepository;
    private final OrderMapper orderMapper;
    private final GetOrdersByCustomerUseCase getOrdersByCustomerUseCase;
    private final GetOrderHistoryUseCase getOrderHistoryUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase, GetOrderUseCase getOrderUseCase, IOrderEventRepository eventRepository, OrderMapper orderMapper, GetOrdersByCustomerUseCase getOrdersByCustomerUseCase, GetOrderHistoryUseCase getOrderHistoryUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
        this.eventRepository = eventRepository;
        this.orderMapper = orderMapper;
        this.getOrdersByCustomerUseCase = getOrdersByCustomerUseCase;
        this.getOrderHistoryUseCase = getOrderHistoryUseCase;
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

    @GetMapping("/{id}")
    public Mono<ResponseEntity<OrderResponseDTO>> getById(@PathVariable String id) {
        return getOrderUseCase.findById(id)
                .map(orderMapper::toResponseDto) // Transformación limpia
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<OrderResponseDTO> getByCustomer(
            @RequestParam String customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return getOrdersByCustomerUseCase.execute(customerId, page, size)
                .map(orderMapper::toResponseDto); // El mapper ya maneja el NPE de los items
    }



    @GetMapping("/{id}/events")
    public Flux<OrderEvent> getHistory(@PathVariable String id) {
        return getOrderHistoryUseCase.execute(id);
    }

}
