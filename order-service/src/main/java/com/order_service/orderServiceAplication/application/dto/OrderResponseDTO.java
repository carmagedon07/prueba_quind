package com.order_service.orderServiceAplication.application.dto;

import com.order_service.orderServiceAplication.domain.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderResponseDTO {
    private String id;
    private String customerId;
    private OrderStatus status;
    private Double totalAmount;
    private List<OrderItemResponseDTO> items;
    private LocalDateTime createdAt;
}
