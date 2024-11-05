package giza.client.platform.survey.api.service;

import giza.client.platform.survey.model.dto.SurveyDto;
import giza.client.platform.survey.model.vto.SurveyAllAnswersVTO;
import giza.client.platform.survey.model.vto.SurveyVTO;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public interface ISurveyService {
    Long createSurvey(SurveyDto obj);

    void updateSurvey(SurveyDto obj, Long id);

    void closeSurvey(Long id);

    void deleteSurvey(Long id);

    SurveyVTO getSurvey(Long id);

    void distributeSurvey(Long surveyId,Long[] recipients);

    void submitSurvey(Long surveyId,Long userId);

    List<SurveyAllAnswersVTO> getSurveyAnswers(Long surveyId);

    void distribute() throws ParseException;

}
