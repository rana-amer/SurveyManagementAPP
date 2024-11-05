package giza.summer.training.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "survey_distribution")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDistribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "created_on")
    private Date createdOn;

    @Basic
    @Column(name = "submit_on")
    private Date submitOn;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "survey_answer_status_id")
    private SurveyAnswerStatus surveyAnswerStatus;
}
