package com.notification_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;
    private Long orderId;
    private String type; // "EMAIL", "PUSH"
    private String message;
    private String status; // "SENT", "FAILED"
    private LocalDateTime createdAt;

}
