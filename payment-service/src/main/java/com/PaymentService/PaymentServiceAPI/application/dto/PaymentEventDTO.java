package com.PaymentService.PaymentServiceAPI.application.dto;

import java.io.Serializable;

public record PaymentEventDTO(
        Long orderId,         // ID de la orden que se está pagando
        String paymentId,     // ID interno generado por la pasarela de pagos
        boolean success,      // Resultado de la transacción
        String message        // Motivo del éxito o fallo (ej: "Saldo insuficiente")
)implements Serializable {
}
