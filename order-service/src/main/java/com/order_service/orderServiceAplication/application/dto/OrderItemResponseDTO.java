package com.order_service.orderServiceAplication.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponseDTO {
    private String productId;
    private Integer quantity;
    private Double price;
}
