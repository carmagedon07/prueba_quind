package com.notification_service.application.dto;

public record PaymentEventDTO(
        Long orderId,       // ID de la orden (proviene de Postgres en OrderService)
        String paymentId,   // ID de la auditoría (proviene de MongoDB en PaymentService)
        boolean success,    // Resultado de la transacción
        String message
) {
}
