package giza.client.platform.survey.model.entity;

import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

@Entity
@Table(name = "question_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionType {
    @Id
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "title")
    private String title;

}
