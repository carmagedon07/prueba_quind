package com.notification_service;

import com.notification_service.application.dto.OrderPlacedEvent;
import com.notification_service.domain.model.Notification;
import com.notification_service.domain.repository.INotificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public class NotificationServiceIntegrationTest {

    @Container
    static KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.4.0")
    );

    @Container
    static MongoDBContainer mongo = new MongoDBContainer(
            DockerImageName.parse("mongo:6.0")
    );

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
        registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
    }

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private INotificationRepository notificationRepository;

    @Test
    void shouldProcessOrderPlacedEvent() {
        // 1. Preparar el evento
        OrderPlacedEvent event = new OrderPlacedEvent("ORD-123", "cliente@email.com", 500.0);

        // 2. Enviar el evento al tópico de Kafka
        kafkaTemplate.send("order-placed-topic", event);

        // 3. Verificar que se guardó la notificación en MongoDB
        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            List<Notification> notifications = notificationRepository
                    .findAll()
                    .collectList()
                    .block();

            assertThat(notifications).isNotEmpty();
            assertThat(notifications.get(0).getOrderId()).isEqualTo("ORD-123");
        });
    }
}