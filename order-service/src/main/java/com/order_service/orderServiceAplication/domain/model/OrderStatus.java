package com.order_service.orderServiceAplication.domain.model;

public enum OrderStatus {
    PENDING, CONFIRMED, PAYMENT_PROCESSING, PAID,
    SHIPPED, DELIVERED, CANCELLED, FAILED
}
