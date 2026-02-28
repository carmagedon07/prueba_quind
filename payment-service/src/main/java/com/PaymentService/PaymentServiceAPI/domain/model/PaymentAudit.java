package com.PaymentService.PaymentServiceAPI.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "payments_audit")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentAudit {
    @Id
    private String id;
    private Long orderId;
    private BigDecimal amount;
    private String status; // "APPROVED", "REJECTED"
    private String gatewayResponse;
    private LocalDateTime createdAt;
}
