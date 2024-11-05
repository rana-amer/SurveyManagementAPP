package giza.client.platform.survey.core.repository;

import giza.client.platform.survey.model.entity.SurveyDistribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyDistributionRepository extends JpaRepository<SurveyDistribution, Long> {
    SurveyDistribution findBySurveyIdAndUserId(Long surveyId, Long userId);
    Optional<SurveyDistribution> findById(Long id);

    List<SurveyDistribution> findBySurveyId(Long surveyId);
}
