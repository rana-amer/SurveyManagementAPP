package giza.client.platform.notification.core.repository;

import giza.client.platform.notification.model.entity.NotificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRequestRepository extends JpaRepository<NotificationRequest, Integer> {


}
