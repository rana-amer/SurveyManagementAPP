package giza.client.platform.notification.core.service;


import giza.notification.sender.model.dto.NotificationDTO;

public interface INotificationService {

    void sendNotification (int notificationObjectId);

    Integer createNotificationObject (NotificationDTO notificationDTO);

}
