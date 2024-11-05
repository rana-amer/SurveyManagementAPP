package giza.client.platform.notification.model.vto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventVTO {
    private Integer id;
    private String title;
    private String subject;
    private String body;

}
