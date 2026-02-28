package com.PaymentService.PaymentServiceAPI.application.dto;

import java.math.BigDecimal;

public record OrderEventDTO(
        Long id,
        String customerId,
        BigDecimal totalAmount,
        String status

) {
}
