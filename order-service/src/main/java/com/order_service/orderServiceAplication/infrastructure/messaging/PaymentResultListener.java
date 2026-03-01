package com.order_service.orderServiceAplication.infrastructure.messaging;


import com.order_service.orderServiceAplication.application.dto.PaymentEventDTO;
import com.order_service.orderServiceAplication.application.usecase.SaveOrderEventUseCase;
import com.order_service.orderServiceAplication.domain.model.OrderStatus;
import com.order_service.orderServiceAplication.domain.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentResultListener {

    private final IOrderRepository orderRepository; // Tu repositorio R2DBC
    private final SaveOrderEventUseCase saveEventUseCase ;

    @KafkaListener(topics = "payment-response-topic", groupId = "order-service-group")
    public void handlePaymentResponse(PaymentEventDTO response) {
        log.info("Procesando respuesta de pago para Orden ID: {}", response.orderId());

        // 1. Actualizamos el estado en Postgres (Fuente de verdad del estado actual)
        orderRepository.findById(String.valueOf(response.orderId()))
                .flatMap(order -> {
                    order.setStatus(OrderStatus.valueOf(response.success() ? "PAID" : "CANCELED"));
                    return orderRepository.save(order);
                })
                // 2. Guardamos el evento en MongoDB (Fuente de verdad del histórico)
                .flatMap(updatedOrder -> saveEventUseCase.execute(response))
                .doOnSuccess(event -> log.info("Orden {} actualizada y evento registrado", response.orderId()))
                .doOnError(e -> log.error("Error al cerrar ciclo de orden {}: {}", response.orderId(), e.getMessage()))
                .subscribe();
    }
}
