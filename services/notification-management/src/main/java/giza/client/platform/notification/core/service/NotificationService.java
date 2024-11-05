package giza.client.platform.notification.core.service;


import giza.client.platform.notification.core.repository.EventRepository;
import giza.client.platform.notification.core.repository.NotificationRequestRepository;
import giza.client.platform.notification.core.repository.NotificationRepository;
import giza.client.platform.notification.model.dto.MailDTO;
import giza.client.platform.notification.model.entity.Event;
import giza.client.platform.notification.model.entity.Notification;
import giza.client.platform.notification.model.entity.NotificationRequest;
import giza.notification.sender.model.dto.NotificationDTO;
import giza.user.sync.core.repository.UserRepository;
import giza.user.sync.model.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationService implements INotificationService{

    private final NotificationRequestRepository repo;
    private final NotificationRepository notificationRepo;
    private final UserRepository userRepo;

    private final ModelMapper mapper;

    private final EmailService emailService;

    private final EventRepository eventRepo;


    @Override
    public Integer createNotificationObject (NotificationDTO notificationDTO){
        Event event = eventRepo.findById(notificationDTO.getEventId()).get();
        NotificationRequest notificationRequest = mapper.map(notificationDTO, NotificationRequest.class);
        notificationRequest.setEvent(event);
         notificationRequest =repo.save(notificationRequest);
        for (Integer notifierId:notificationDTO.getNotifiers()){
            User notifier = userRepo.findById(Long.valueOf(notifierId)).get();
            Notification notification= new Notification();
            notification.setNotificationRequest(notificationRequest);
            notification.setNotifier(notifier);
            notification.setSubject(event.getSubject());
            String message = event.getBody();
            message = message.replaceAll("\\{username}",notifier.getUsername());
            message = message.replaceAll("\\{entity_id}",notificationDTO.getEntityId().toString());
            if(notificationDTO.getParameters().containsKey("deadline")){
                message = message.replaceAll("\\{deadline}",notificationDTO.getParameters().get("deadline"));
            }
            notification.setBody(message);
            notificationRepo.save(notification);

        }
        return notificationRequest.getId();


    }


    @Override
    public void sendNotification (int notificationObjectId){
        Optional<NotificationRequest> _notificationObject = repo.findById(notificationObjectId);
        if(_notificationObject.isPresent()){
//            NotificationRequest notificationRequest = _notificationObject.get();
           List<Notification> notifications = notificationRepo.findAllByNotificationRequestId(notificationObjectId);
           for(Notification notification:notifications){
                   MailDTO mailDTO = new MailDTO();
                   mailDTO.setSubject(notification.getSubject());
                   mailDTO.setText(notification.getBody());
                   mailDTO.setTo(notification.getNotifier().getEmail());
                   emailService.sendSimpleMessage(mailDTO);

           }
        }
    }



}
