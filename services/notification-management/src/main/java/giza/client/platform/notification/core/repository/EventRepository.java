package giza.client.platform.notification.core.repository;

import giza.client.platform.notification.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
