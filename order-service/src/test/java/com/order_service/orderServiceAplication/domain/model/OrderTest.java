package com.order_service.orderServiceAplication.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {

    @Test
    void shouldReturnTrueWhenOrderIsPending() {
        Order order = Order.builder().status(OrderStatus.PENDING).build();
        assertTrue(order.canBeCancelled());
    }

    @Test
    void shouldReturnFalseWhenOrderIsShipped() {
        Order order = Order.builder().status(OrderStatus.SHIPPED).build();
        assertFalse(order.canBeCancelled());
    }
}
