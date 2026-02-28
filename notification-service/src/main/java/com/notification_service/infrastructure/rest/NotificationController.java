package com.notification_service.infrastructure.rest;

import com.notification_service.application.usecase.GetNotificationsUseCase;
import com.notification_service.domain.model.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final GetNotificationsUseCase getNotificationsUseCase;

    @GetMapping
    public Flux<Notification> getByOrderId(@RequestParam Long orderId) {
        return getNotificationsUseCase.execute(orderId);
    }
}
