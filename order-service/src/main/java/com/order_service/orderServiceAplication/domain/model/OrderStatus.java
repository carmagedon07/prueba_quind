package com.order_service.orderServiceAplication.domain.model;

public enum OrderStatus {
    PENDING,    // Pedido creado pero no procesado
    PROCESSING, // Pedido en proceso de preparación
    SHIPPED,    // Pedido enviado al cliente
    DELIVERED,  // Pedido entregado al cliente
    CONFIRMED, CANCELLED   // Pedido cancelado
}
