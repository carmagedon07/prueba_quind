package com.notification_service.domain.repository;

public interface INotificationSender {
    void sendNotification(String destination, String message);
}
