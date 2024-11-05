package giza.summer.training.model.vto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyVTO {
    private String title;
    private String description;
    private Date createdOn;
    private Integer statusId;
    private Date startDate;
    private Date endDate;
    private Date closedOn;

    private Long surveyStatus;



}
