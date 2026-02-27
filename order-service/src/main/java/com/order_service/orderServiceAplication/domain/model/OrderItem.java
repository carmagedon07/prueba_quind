package com.order_service.orderServiceAplication.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderItem {
    private String productId;
    private Integer quantity;
    private Double price; // Precio unitario al momento de la compra
}
