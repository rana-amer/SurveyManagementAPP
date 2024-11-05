package giza.client.platform.survey.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question_choice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "choice")
    private String choice;

    @Basic
    @Column(name = "choice_order")
    private Integer choiceOrder;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
