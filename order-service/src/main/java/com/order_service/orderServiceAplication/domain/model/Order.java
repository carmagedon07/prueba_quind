package com.order_service.orderServiceAplication.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< HEAD
import java.util.List;

import com.order_service.orderServiceAplication.domain.model.OrderItem;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Order {
    private String id;
=======
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Order {

    private Long id;
>>>>>>> develop_checkout_2
    private String customerId;
    private List<OrderItem> items;
    private Double totalAmount;
    private OrderStatus status;
<<<<<<< HEAD
=======
    private LocalDateTime createdAt;

    // Métodos de lógica de negocio (no solo getters y setters)
    public void calculateTotal() {
        this.totalAmount = items.stream()
                .mapToDouble(OrderItem::getPrice)
                .sum();
    }
>>>>>>> develop_checkout_2

    // Lógica de negocio: Una orden solo se cancela si está en PENDING o CONFIRMED
    public void cancel() {
        if (this.status == OrderStatus.PENDING || this.status == OrderStatus.CONFIRMED) {
            this.status = OrderStatus.CANCELLED;
        } else {
            throw new IllegalStateException("No se puede cancelar en estado: " + this.status);
        }
    }

<<<<<<< HEAD
    public void calculateTotal() {
        this.totalAmount = items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
=======
    public boolean canBeCancelled() {
        return this.status == OrderStatus.PENDING ||
                this.status == OrderStatus.CONFIRMED;
    }


>>>>>>> develop_checkout_2
}
