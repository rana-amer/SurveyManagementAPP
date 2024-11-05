package giza.client.platform.survey.core.repository;

import giza.client.platform.survey.model.entity.SurveyAnswers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyAnswersRepository  extends JpaRepository<SurveyAnswers, Long> {
    List<SurveyAnswers> findBySurveyDistributionIdIn(List<Long> surveyDistributionIds);
}
