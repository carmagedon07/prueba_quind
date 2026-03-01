package com.notification_service.infrastructure.persistence.adpter;

import com.notification_service.domain.model.Notification;
import com.notification_service.domain.repository.INotificationRepository;
import com.notification_service.infrastructure.persistence.repository.IMongoNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationAdapter implements INotificationRepository {

    private final IMongoNotificationRepository mongoRepository;

    @Override
    public Mono<Notification> save(Notification notification) {
        return mongoRepository.save(notification);
    }

    @Override
    public Flux<Notification> findByOrderId(Long orderId) {
        return mongoRepository.findByOrderId(orderId);
    }

    @Override
    public Flux<Notification> findAll() {
        return mongoRepository.findAll()
                .doOnError(e -> log.error("Error al recuperar todas las notificaciones", e));
    }

}
