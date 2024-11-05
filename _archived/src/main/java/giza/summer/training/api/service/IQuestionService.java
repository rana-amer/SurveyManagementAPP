package giza.summer.training.api.service;

import giza.summer.training.model.dto.QuestionDTO;
import giza.summer.training.model.dto.QuestionFilterDTO;
import giza.summer.training.model.vto.QuestionFilterVTO;
import giza.summer.training.model.vto.QuestionVTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IQuestionService {
    void createQuestion(QuestionDTO question, Long surveyId);

    void updateQuestionById(QuestionDTO question, Long surveyId, Long questionId);

    void deleteQuestionById(Long questionId, Long surveyId);

    QuestionVTO getQuestionById(Long surveyId, Long questionId);
//    List<QuestionVTO> getQuestions(Date createdOn, Integer pageNum, Integer pageSize);
    QuestionFilterVTO getAllQuestionsFilters(QuestionFilterDTO questionFilterDTO);

    }
