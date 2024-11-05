package giza.summer.training.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "survey_answer_status")
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAnswerStatus {
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "title")
    private String title;
}
