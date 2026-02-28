package com.PaymentService.PaymentServiceAPI.infrastructure.messaging;

import com.PaymentService.PaymentServiceAPI.application.dto.OrderEventDTO;
import com.PaymentService.PaymentServiceAPI.domain.model.PaymentAudit;
import com.PaymentService.PaymentServiceAPI.domain.repository.IPaymentAuditRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@Slf4j
public class OrderListener {

    private final PaymentPublisher paymentPublisher;
    private final IPaymentAuditRepository auditRepository;

    public OrderListener(PaymentPublisher paymentPublisher, IPaymentAuditRepository auditRepository) {
        this.paymentPublisher = paymentPublisher;
        this.auditRepository = auditRepository;
    }

    @KafkaListener(topics = "order-created-topic", groupId = "payment-group")
    public void handleOrderCreated(OrderEventDTO orderEvent) {
        log.info("Procesando pago para la orden: {}", orderEvent.id());

        BigDecimal threshold = BigDecimal.valueOf(100.0);
        boolean isSuccess = orderEvent.totalAmount() != null &&
                orderEvent.totalAmount().compareTo(threshold) >= 0;

        // 1. Creamos el registro de auditoría
        PaymentAudit audit = PaymentAudit.builder()
                .orderId(orderEvent.id())
                .amount(orderEvent.totalAmount())
                .status(isSuccess ? "APPROVED" : "REJECTED")
                .createdAt(LocalDateTime.now())
                .gatewayResponse(isSuccess ? "Monto válido" : "Monto insuficiente ( < 100)")
                .build();

        // 2. Guardamos en MongoDB y LUEGO publicamos a Kafka
        auditRepository.save(audit)
                .doOnSuccess(saved -> {
                    paymentPublisher.publishPaymentResponse(orderEvent.id(), isSuccess);
                    log.info("Pago persistido y respuesta enviada para orden {}", orderEvent.id());
                })
                .doOnError(e -> log.error("Error guardando auditoría: {}", e.getMessage()))
                .subscribe(); // Ejecuta el flujo reactivo
    }


}
