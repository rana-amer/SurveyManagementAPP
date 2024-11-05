package giza.client.platform.survey.core.repository;

import giza.client.platform.survey.model.entity.Question;
import giza.client.platform.survey.model.entity.QuestionChoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionChoiceRepository extends JpaRepository<QuestionChoice, Long> {
    List<QuestionChoice> findByQuestionId(Long questionId);
}
