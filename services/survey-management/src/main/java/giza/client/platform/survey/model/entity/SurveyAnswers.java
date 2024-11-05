package giza.client.platform.survey.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "survey_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAnswers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_distribution_id")
    private SurveyDistribution surveyDistribution;

    @Basic
    @Column(name = "value")
    private String value;


}
