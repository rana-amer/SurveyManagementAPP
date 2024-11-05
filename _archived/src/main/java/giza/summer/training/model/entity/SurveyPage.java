package giza.summer.training.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyPage {
    private int pageNumber=0;
    private int pageSize=10;
    private Sort.Direction sortDir= Sort.Direction.ASC;
    private String sortBy="createdOn";
    private String title;
    private Date startDateFrom;
    private Date endDateTo;
    private Date createdOnFrom;
    private Date createdOnTo;

}
