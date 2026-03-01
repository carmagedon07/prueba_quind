package com.notification_service.infrastructure.persistence.adpter;

import com.notification_service.domain.repository.INotificationSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService implements INotificationSender {
    @Override
    public void sendNotification(String destination, String message) {
        log.info("Enviando correo a " + destination + ": " + message);

    }
}
