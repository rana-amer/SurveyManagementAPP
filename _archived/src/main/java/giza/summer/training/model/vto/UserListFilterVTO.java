package giza.summer.training.model.vto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class UserListFilterVTO {
    private Integer count;
    private List<UserVTO> userVTOS;
}
