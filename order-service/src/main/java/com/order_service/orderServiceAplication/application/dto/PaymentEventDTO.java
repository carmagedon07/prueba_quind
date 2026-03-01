package com.order_service.orderServiceAplication.application.dto;

public record PaymentEventDTO(
        Long orderId,       // ID de la orden en Postgres
        String paymentId,   // ID de auditoría generado en MongoDB (opcional)
        boolean success,    // Resultado de la validación (> 100)
        String message
) {
}
