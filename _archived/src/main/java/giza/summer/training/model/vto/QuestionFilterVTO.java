package giza.summer.training.model.vto;


import giza.summer.training.model.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class QuestionFilterVTO {

    private Integer count;
    private List<QuestionVTO> questionVTOS;
}
