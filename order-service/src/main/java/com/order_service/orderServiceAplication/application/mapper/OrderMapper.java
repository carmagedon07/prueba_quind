package com.order_service.orderServiceAplication.application.mapper;

import com.order_service.orderServiceAplication.application.dto.OrderItemResponseDTO;
import com.order_service.orderServiceAplication.application.dto.OrderResponseDTO;
import com.order_service.orderServiceAplication.domain.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component

public class OrderMapper {
    public OrderResponseDTO toResponseDto(Order order) {
        if (order == null) return null;

        return OrderResponseDTO.builder()
                .id(String.valueOf(order.getId()))
                .customerId(order.getCustomerId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                // Solución: Validar si la lista es nula antes de hacer stream
                .items(order.getItems() == null ? List.of() : order.getItems().stream()
                        .map(item -> OrderItemResponseDTO.builder()
                                .productId(item.getProductId())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
