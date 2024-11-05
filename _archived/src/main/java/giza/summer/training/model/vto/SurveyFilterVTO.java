package giza.summer.training.model.vto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class SurveyFilterVTO {
    private Integer count;
    private List<SurveyVTO> surveyVTOS;
}
