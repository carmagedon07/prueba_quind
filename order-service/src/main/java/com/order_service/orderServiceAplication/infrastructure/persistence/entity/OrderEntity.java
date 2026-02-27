package com.order_service.orderServiceAplication.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderEntity {
    @Id
    private Long id;
    private String customerId;
    private Double totalAmount;
    private String status;

}
