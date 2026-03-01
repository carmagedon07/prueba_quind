package com.order_service.orderServiceAplication.application.usecase;

import com.order_service.orderServiceAplication.domain.model.OrderEvent;
import com.order_service.orderServiceAplication.domain.repository.IOrderEventRepository;
import com.order_service.orderServiceAplication.application.dto.PaymentEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SaveOrderEventUseCase {

    private final IOrderEventRepository eventRepository;

    public Mono<OrderEvent> execute(PaymentEventDTO paymentResponse) {
        OrderEvent event = OrderEvent.builder()
                .orderId(paymentResponse.orderId().toString())
                .eventType("PAYMENT_RESPONSE_RECEIVED")
                .status(paymentResponse.success() ? "SUCCESS" : "FAILED")
                .description(paymentResponse.message())
                .createdAt(LocalDateTime.now())
                .build();

        return eventRepository.save(event);
    }

}
