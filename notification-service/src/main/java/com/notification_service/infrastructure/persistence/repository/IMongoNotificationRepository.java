package com.notification_service.infrastructure.persistence.repository;

import com.notification_service.domain.model.Notification;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface IMongoNotificationRepository extends ReactiveMongoRepository<Notification, String> {

    Flux<Notification> findByOrderId(Long orderId);


}
