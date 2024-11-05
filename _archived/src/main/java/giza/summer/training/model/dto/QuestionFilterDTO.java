package giza.summer.training.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionFilterDTO {
    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "createdOn";
    private Integer questionOrder;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    private Date createdOnFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    private Date createdOnTo;
}
