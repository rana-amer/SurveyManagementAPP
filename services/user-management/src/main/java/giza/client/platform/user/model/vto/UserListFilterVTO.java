package giza.client.platform.user.model.vto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class UserListFilterVTO {
    private Integer count;
    private List<UserVTO> userVTOS;
}
