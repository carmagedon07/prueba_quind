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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(
        name = "Orders",
        description = "Gestión de órdenes y sus eventos. " +
                "Permite crear órdenes, consultar órdenes por ID o por cliente, " +
                "y obtener el historial de eventos asociados a una orden (incluye id, orderId, type, message, status y createdAt). " +
                "Si no existen eventos para una orden, se retorna un arreglo vacío ([])."
)
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
    @Operation(
            summary = "Crea una nueva orden",
            description = """
            Crea una nueva orden en el sistema.
            - Requiere customerId y lista de items con productId y quantity.
            - Retorna la orden creada con su ID, customerId, totalAmount y status.
            """
    )
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
    @Operation(
            summary = "Obtiene una orden por su ID",
            description = "Retorna los detalles de una orden específica basada en su ID."
    )
    public Mono<ResponseEntity<OrderResponseDTO>> getById(@PathVariable String id) {
        return getOrderUseCase.findById(id)
                .map(orderMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(
            summary = "Obtiene órdenes por ID de cliente",
            description = "Retorna una lista paginada de órdenes para un cliente específico."
    )
    public Flux<OrderResponseDTO> getByCustomer(
            @RequestParam String customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return getOrdersByCustomerUseCase.execute(customerId, page, size)
                .map(orderMapper::toResponseDto);
    }

    @GetMapping("/{id}/events")
    @Operation(
            summary = "Obtiene el historial de eventos de una orden",
            description = """
            Retorna la lista de eventos asociados a un orderId específico.
            - Si no existen, devuelve lista vacía [].
            - Formato JSON con campos: id, orderId, type, message, status, createdAt.
            """
    )
    public Flux<OrderEvent> getHistory(@PathVariable String id) {
        return getOrderHistoryUseCase.execute(id);
    }
}