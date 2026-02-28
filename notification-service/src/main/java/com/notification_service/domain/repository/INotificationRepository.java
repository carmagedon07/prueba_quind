package com.notification_service.domain.repository;

import com.notification_service.domain.model.Notification;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface INotificationRepository {

    Mono<Notification> save(Notification notification);
    Flux<Notification> findByOrderId(Long orderId);
}
