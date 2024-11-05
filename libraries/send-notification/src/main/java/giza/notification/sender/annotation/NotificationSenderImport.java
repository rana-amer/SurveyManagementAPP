package giza.notification.sender.annotation;

import giza.notification.sender.service.SendNotification;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SendNotification.class})
public class NotificationSenderImport {
}
