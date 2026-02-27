package com.order_service.orderServiceAplication.application.dto;

public record OrderResponse(String id,
                            String customerId,
                            Double totalAmount,
                            String status) {

}
