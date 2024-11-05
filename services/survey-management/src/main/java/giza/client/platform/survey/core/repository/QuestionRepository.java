package giza.client.platform.survey.core.repository;
import giza.client.platform.survey.model.dto.SurveyQuestionItemDTO;
import giza.client.platform.survey.model.entity.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    //List<Question> findAllByCreatedOn(Date createdOn, Pageable pageable);
    List<Question> findBySurveyIdOrderByQuestionOrder(Long surveyId);
}