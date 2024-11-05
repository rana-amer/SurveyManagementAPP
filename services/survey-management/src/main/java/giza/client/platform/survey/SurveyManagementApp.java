package giza.client.platform.survey;

import giza.jms.config.ActiveMQClientImport;
import giza.notification.sender.annotation.NotificationSenderImport;
import giza.user.authentication.annotation.UserAuthenticationImport;
import giza.user.sync.annotation.UserSyncImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({UserSyncImport.class, ActiveMQClientImport.class, NotificationSenderImport.class, UserAuthenticationImport.class})

@SpringBootApplication
public class SurveyManagementApp {

    public static void main(String[] args) {
        SpringApplication.run(SurveyManagementApp.class, args);
    }

}
