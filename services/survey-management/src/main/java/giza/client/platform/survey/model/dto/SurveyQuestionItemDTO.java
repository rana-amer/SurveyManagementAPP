package giza.client.platform.survey.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyQuestionItemDTO {

    private String questionText;
    @NotBlank(message = "Is Mandatory field is mandatory")
    private Boolean isMandatory;
    @NotBlank(message = "Question Order is mandatory")
    private Integer questionOrder;
    @NotBlank(message = "Is Selecting MultiChoice Allowed is mandatory")
    private Boolean isSelectingMultiChoiceAllowed;
    private List<QuestionChoiceDTO> choices;
}
