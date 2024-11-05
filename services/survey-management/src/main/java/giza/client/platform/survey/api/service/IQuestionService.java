package giza.client.platform.survey.api.service;

import giza.client.platform.survey.model.dto.QuestionDTO;
import giza.client.platform.survey.model.dto.SurveyAnswersDTO;
import giza.client.platform.survey.model.dto.SurveyQuestionItemDTO;
import giza.client.platform.survey.model.entity.Question;
import giza.client.platform.survey.model.vto.QuestionVTO;
import giza.client.platform.survey.model.dto.QuestionDTO;
import giza.client.platform.survey.model.vto.QuestionVTO;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IQuestionService {
    void createQuestion(QuestionDTO question, Long surveyId);

    void updateQuestionById(QuestionDTO question, Long surveyId, Long questionId);

    void deleteQuestionById(Long questionId, Long surveyId);

    QuestionVTO getQuestionById(Long surveyId, Long questionId);
    //    List<QuestionVTO> getQuestions(Date createdOn, Integer pageNum, Integer pageSize);
//    QuestionFilterVTO getAllQuestionsFilters(QuestionFilterDTO questionFilterDTO);
    List<SurveyQuestionItemDTO> getSurveyQuestions(Long surveyId);

    void submitWithAnswers(Long surveyDistributionId, List<SurveyAnswersDTO> surveyAnswers);

}
