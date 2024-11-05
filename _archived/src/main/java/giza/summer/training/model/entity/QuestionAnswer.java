package giza.summer.training.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question_answer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "answer")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "survey_distribution_id")
    private SurveyDistribution surveyDistribution;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

}
