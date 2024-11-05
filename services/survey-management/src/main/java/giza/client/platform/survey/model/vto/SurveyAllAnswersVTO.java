package giza.client.platform.survey.model.vto;

import giza.client.platform.survey.model.dto.SurveyAnswersDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyAllAnswersVTO {
    List<SurveyAnswersDTO> surveyAnswersDTOList;
}
