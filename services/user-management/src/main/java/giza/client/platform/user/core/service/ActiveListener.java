package giza.client.platform.user.core.service;

import giza.client.platform.user.model.dto.UserDTO;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class ActiveListener {
    private final UserService userService;

    public ActiveListener(UserService userService) {
        this.userService = userService;
    }

//    @JmsListener(destination = "giza.notify.ntf.mgt")
//    public void onReceiveNotification (UserDTO userDTO){
//
//
////        notificationService.sendNotification(notificationObjectId);
//    }
}
