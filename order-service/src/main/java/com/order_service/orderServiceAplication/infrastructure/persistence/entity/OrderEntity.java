package com.order_service.orderServiceAplication.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;

@Table("orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderEntity {

    @Id
    private Long id;
    private String customerId;
    private Double totalAmount;
    private String status;
    // añade aquí otros campos según tu dominio (por ejemplo: orderNumber, status, total, etc.)
}
