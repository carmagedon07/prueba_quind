package com.order_service.orderServiceAplication.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderEvent {
    private String id; // ID del evento
    private String orderId;
    private String eventType; // "ORDER_CREATED", "ORDER_CANCELLED", etc.
    private String description;
    private LocalDateTime createdAt;
}
