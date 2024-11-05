package giza.client.platform.survey.core.repository;

import giza.client.platform.survey.model.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository< Survey, Long> {
    boolean existsByTitle(String title);

    List<Survey> findByStartDate(Date startDate);
}
