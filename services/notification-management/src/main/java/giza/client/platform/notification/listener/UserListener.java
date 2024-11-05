package giza.client.platform.notification.listener;

//import giza.client.platform.survey.model.dto.UserDTO;
import giza.user.sync.core.service.UserService;
import giza.user.sync.model.dto.UserSyncDTO;
import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserListener {
    private final UserService userService;
    @JmsListener(destination = "giza.ntf.save.user")
    public void onReceive(UserSyncDTO userDTO){
        userService.createUser(userDTO);
    }
}
