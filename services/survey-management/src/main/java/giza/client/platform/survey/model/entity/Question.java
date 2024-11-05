package giza.client.platform.survey.model.entity;



        import jakarta.persistence.*;
        import jakarta.validation.constraints.NotBlank;
        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import java.time.LocalDateTime;
        import java.util.Date;

@Entity
@Table(name = "question")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "question_text")
    private String questionText;

    @Basic
    @Column(name = "is_mandatory")
    private Boolean isMandatory;

    @Basic
    @Column(name = "question_order")
    private Integer questionOrder;

    @Basic
    @Column(name = "is_selecting_multi_choice_allowed")
    private Boolean isSelectingMultiChoiceAllowed;



    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;


    @ManyToOne
    @JoinColumn(name = "question_type_id")
    private QuestionType questionType;


}
