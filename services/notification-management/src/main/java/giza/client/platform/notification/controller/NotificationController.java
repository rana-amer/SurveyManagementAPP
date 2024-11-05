package giza.client.platform.notification.controller;

import giza.client.platform.notification.core.service.EmailService;
import giza.client.platform.notification.core.service.NotificationService;
import giza.client.platform.notification.model.dto.MailDTO;
import giza.notification.sender.model.dto.NotificationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final EmailService emailService;
    private final NotificationService notificationService;
    private final JmsTemplate jms;

    public NotificationController(EmailService emailService, NotificationService notificationService, JmsTemplate jms) {
        this.emailService = emailService;
        this.notificationService = notificationService;
        this.jms = jms;
    }

    @PostMapping("/send")
    public ResponseEntity<?> getUserByUsername(@RequestBody MailDTO mailDTO) {
        emailService.sendSimpleMessage(mailDTO);

        return ResponseEntity.ok("Email Sent");
    }

    @PostMapping("/notify")
    public ResponseEntity<?> sendNotification (@RequestBody NotificationDTO notificationDTO) {
        Integer id = notificationService.createNotificationObject(notificationDTO);
        notificationService.sendNotification(id);

        return ResponseEntity.ok("Email Sent");
    }

    @PostMapping("/notify/jms")
    public ResponseEntity<?> sendNotificationByJMS (@RequestBody NotificationDTO notificationDTO) {
        jms.convertAndSend("giza.notify.ntf.mgt",notificationDTO);

        return ResponseEntity.ok("Email Sent");
    }
}