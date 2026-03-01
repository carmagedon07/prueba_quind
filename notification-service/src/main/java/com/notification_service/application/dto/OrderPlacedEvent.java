package com.notification_service.application.dto;

public record OrderPlacedEvent(
        String orderId,
        String customerEmail,
        Double totalAmount
) {
}
