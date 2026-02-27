package com.order_service.orderServiceAplication.application.dto;

public record OrderResponse(Long id,
                            String customerId,
                            Double totalAmount,
                            String status) {
}
