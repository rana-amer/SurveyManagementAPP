package giza.summer.training.model.vto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionVTO {
    private Long id;
    private String questionText;
    private Long surveyId;
    private Long questionTypeID;
    private Boolean isMandatory;
    private Integer questionOrder;
    private Boolean isSelectingMultiChoiceAllowed;
    private Date createdOn;

//    private List<QuestionChoiceDTO> choices;
}
