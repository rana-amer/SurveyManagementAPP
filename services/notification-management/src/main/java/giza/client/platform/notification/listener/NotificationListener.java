package giza.client.platform.notification.core.service;




import giza.notification.sender.model.dto.NotificationDTO;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

    private final NotificationService notificationService;

    public NotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @JmsListener(destination = "giza.notify.ntf.mgt")
    public void onReceiveNotification (NotificationDTO notificationDTO){

        Integer id = notificationService.createNotificationObject(notificationDTO);
        notificationService.sendNotification(id);
    }

}
