package com.order_service.orderServiceAplication.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.order_service.orderServiceAplication.domain.model.OrderItem;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Order {
    private String id;
    private String customerId;
    private List<OrderItem> items;
    private Double totalAmount;
    private OrderStatus status;

    // Lógica de negocio: Una orden solo se cancela si está en PENDING o CONFIRMED
    public void cancel() {
        if (this.status == OrderStatus.PENDING || this.status == OrderStatus.CONFIRMED) {
            this.status = OrderStatus.CANCELLED;
        } else {
            throw new IllegalStateException("No se puede cancelar en estado: " + this.status);
        }
    }

    public void calculateTotal() {
        this.totalAmount = items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}
