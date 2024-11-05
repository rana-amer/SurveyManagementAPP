package giza.client.platform.survey.model.dto;


        import lombok.AllArgsConstructor;
        import lombok.Builder;
        import lombok.Data;
        import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionChoiceDTO {
    private String choice;
    private Integer order;
}
