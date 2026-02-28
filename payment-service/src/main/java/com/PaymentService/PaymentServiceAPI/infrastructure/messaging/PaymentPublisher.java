package com.PaymentService.PaymentServiceAPI.infrastructure.messaging;

import com.PaymentService.PaymentServiceAPI.application.dto.PaymentEventDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPaymentResponse(Long OrderId, boolean success){
        PaymentEventDTO response = new PaymentEventDTO(
                OrderId,
                UUID.randomUUID().toString(),
                success,
            success ? "PAGANDO": "FONDOS INSUFICIENTES");
        kafkaTemplate.send("payment-response-topic", response);

    }
}
