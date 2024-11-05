package giza.summer.training.model.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDTO {
    @NotBlank(message = "Title required")

    private String title;
    @NotBlank(message = "Description required")
    private String description;
    @NotBlank(message = "startDate required")
    private Date startDate;
    private Date endDate;

    public SurveyDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }
}