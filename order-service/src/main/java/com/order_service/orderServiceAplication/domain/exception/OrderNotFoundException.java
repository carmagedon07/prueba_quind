package com.order_service.orderServiceAplication.domain.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String id) {
        super("La orden con ID " + id + " no fue encontrada.");
    }
}
