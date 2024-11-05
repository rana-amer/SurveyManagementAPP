package giza.summer.training.api.service;

import giza.summer.training.model.dto.SurveyDTO;
import giza.summer.training.model.entity.SurveyPage;
import giza.summer.training.model.vto.SurveyFilterVTO;
import giza.summer.training.model.vto.SurveyVTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface ISurveyService {
    Long createSurvey(SurveyDTO obj);

    void updateSurvey(SurveyDTO obj, Long id);

    void closeSurvey(Long id);

    void deleteSurvey(Long id);

    SurveyVTO getSurvey(Long id);

    List<SurveyVTO> getSurveys(
            int page, int size, Date createdOn, String orderBy, String orderDir);
    SurveyFilterVTO getSurveysWithFilter(SurveyPage surveyPage);

}
