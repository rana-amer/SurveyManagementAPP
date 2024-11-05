package giza.notification.sender.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private Integer eventId;
    private Integer entityId;
    private Date createdOn;
    private Integer actorId;
    private List<Integer> notifiers;
    private Map<String, String> parameters;
}

