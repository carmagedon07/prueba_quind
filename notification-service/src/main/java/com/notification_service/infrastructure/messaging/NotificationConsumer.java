package com.notification_service.infrastructure.messaging;

import com.notification_service.application.dto.PaymentEventDTO;
import com.notification_service.domain.model.Notification;
import com.notification_service.domain.repository.INotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    private final INotificationRepository repository;

    @KafkaListener(topics = "payment-response-topic", groupId = "notification-group")
    public void handlePaymentNotification(PaymentEventDTO event) {
        String msg = event.success() ? "Su pago fue aprobado" : "Su pago fue rechazado";

        Notification notification = Notification.builder()
                .orderId(event.orderId())
                .type("EMAIL")
                .message(msg)
                .status("SENT")
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(notification).subscribe(n -> log.info("Notificación enviada para orden {}", n.getOrderId()));
    }


}
