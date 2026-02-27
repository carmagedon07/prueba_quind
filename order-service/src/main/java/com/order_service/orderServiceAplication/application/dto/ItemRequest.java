package com.order_service.orderServiceAplication.application.dto;

public record ItemRequest(String productId,
                          Integer quantity,
                          Double price) {

}
