package com.order_service.orderServiceAplication.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "order_events")
@Data
@Builder
public class OrderEventLog {
    @Id
    private String id;
    private Long orderId;
    private String eventType; // "PAYMENT_RECEIVED"
    private String status;
    private LocalDateTime timestamp;
}
