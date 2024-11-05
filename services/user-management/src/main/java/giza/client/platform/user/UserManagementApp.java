package giza.client.platform.user;

import giza.jms.config.ActiveMQClientImport;
import giza.notification.sender.annotation.NotificationSenderImport;
import giza.user.authentication.annotation.UserAuthenticationImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({NotificationSenderImport.class, ActiveMQClientImport.class})
@SpringBootApplication
public class UserManagementApp {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApp.class, args);
	}

}
