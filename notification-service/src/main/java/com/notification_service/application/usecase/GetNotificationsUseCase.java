package com.notification_service.application.usecase;

import com.notification_service.domain.model.Notification;
import com.notification_service.domain.repository.INotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GetNotificationsUseCase {

    private final INotificationRepository repository;

    public Flux<Notification> execute(Long orderId) {
        return repository.findByOrderId(orderId);

    }
}
