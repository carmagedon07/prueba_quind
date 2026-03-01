package com.order_service.orderServiceAplication.domain.model;

public enum OrderStatus {
<<<<<<< HEAD
    PENDING, CONFIRMED, PAYMENT_PROCESSING, PAID,
    SHIPPED, DELIVERED, CANCELLED, FAILED
=======
    PENDING,    // Pedido creado pero no procesado
    PROCESSING, // Pedido en proceso de preparación
    SHIPPED,    // Pedido enviado al cliente
    DELIVERED,  // Pedido entregado al cliente
    CONFIRMED, CANCELLED   // Pedido cancelado
>>>>>>> develop_checkout_2
}
