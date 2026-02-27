package com.order_service.orderServiceAplication.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Order {

    private Long id;
    private String customerId;
    private List<OrderItem> items;
    private Double totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;

    // Métodos de lógica de negocio (no solo getters y setters)
    public void calculateTotal() {
        this.totalAmount = items.stream()
                .mapToDouble(OrderItem::getPrice)
                .sum();
    }

    // Lógica de negocio: Una orden solo se cancela si está en PENDING o CONFIRMED
    public void cancel() {
        if (this.status == OrderStatus.PENDING || this.status == OrderStatus.CONFIRMED) {
            this.status = OrderStatus.CANCELLED;
        } else {
            throw new IllegalStateException("No se puede cancelar en estado: " + this.status);
        }
    }

    public boolean canBeCancelled() {
        return this.status == OrderStatus.PENDING ||
                this.status == OrderStatus.CONFIRMED;
    }


}
