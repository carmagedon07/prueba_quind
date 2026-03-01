package com.order_service.orderServiceAplication.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
<<<<<<< HEAD
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
=======
import lombok.NoArgsConstructor;
import lombok.Data;

import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;
>>>>>>> develop_checkout_2

@Table("orders")
@AllArgsConstructor
@NoArgsConstructor
<<<<<<< HEAD
@Builder
@Data
public class OrderEntity {
=======
@Data
public class OrderEntity {

>>>>>>> develop_checkout_2
    @Id
    private Long id;
    private String customerId;
    private Double totalAmount;
    private String status;
<<<<<<< HEAD

=======
    // añade aquí otros campos según tu dominio (por ejemplo: orderNumber, status, total, etc.)
>>>>>>> develop_checkout_2
}
