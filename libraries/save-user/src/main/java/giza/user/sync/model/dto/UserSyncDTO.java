package giza.user.sync.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSyncDTO {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String mobile;
    private Date createdOn;
    private Boolean isActive;
    private String gender;
}
