package giza.notification.sender.service;

import giza.notification.sender.model.dto.NotificationDTO;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SendNotification {

    private final JmsTemplate jms;

    public void sendNotification (NotificationDTO notificationDTO){
        System.out.println("sending notifcation ---------------------------------------");
        jms.convertAndSend("giza.notify.ntf.mgt",notificationDTO);
    }
}
