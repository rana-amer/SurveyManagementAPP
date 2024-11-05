package giza.summer.training.core.service;


import giza.summer.training.api.service.IQuestionService;
import giza.summer.training.core.repository.QuestionCriteriaRepository;
import giza.summer.training.core.repository.QuestionRepository;
import giza.summer.training.model.dto.QuestionDTO;
import giza.summer.training.model.dto.QuestionFilterDTO;
import giza.summer.training.model.entity.Question;
import giza.summer.training.model.entity.QuestionType;
import giza.summer.training.model.entity.Survey;
import giza.summer.training.model.entity.User;
import giza.summer.training.model.exceptions.SurveysException;
import giza.summer.training.model.vto.QuestionFilterVTO;
import giza.summer.training.model.vto.QuestionVTO;
import giza.summer.training.model.vto.UserListFilterVTO;
import giza.summer.training.model.vto.UserVTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionRepository repo;

    private final QuestionCriteriaRepository questionCriteriaRepository;
    private final ModelMapper mapper;


    @Override
    public void createQuestion(QuestionDTO questionDto, Long surveyId) {
        Question question = mapper.map(questionDto, Question.class);
        Survey survey = new Survey();
        survey.setId(surveyId);
        QuestionType questionType = new QuestionType();
        questionType.setId(questionDto.getQuestionTypeId());
        question.setSurvey(survey);
        question.setQuestionType(questionType);
        repo.save(question);
    }

    @Override
    public void updateQuestionById(QuestionDTO questionDto, Long surveyId, Long questionId) {
        Optional<Question> questionExists = repo.findById(questionId);
        Question _questionExists = mapper.map(questionExists, Question.class);
        if(_questionExists == null)
            throw new SurveysException("Question not found");
        if(_questionExists.getSurvey().getId()!=surveyId)
            throw new SurveysException("Question not found");

        Question _question = mapper.map(questionDto, Question.class);
        repo.save(_question);
    }

    @Override
    public void deleteQuestionById(Long surveyId,Long questionId) {
        Optional<Question> questionExists = repo.findById(questionId);
        Question _questionExists = mapper.map(questionExists, Question.class);
        if(_questionExists == null)
            throw new SurveysException("Question not found");
        if(_questionExists.getSurvey().getId()!=surveyId)
            throw new SurveysException("Question not found");

        repo.deleteById(questionId);
    }

    @Override
    public QuestionVTO getQuestionById(Long surveyId, Long questionId) {
        Optional<Question> questionExists = repo.findById(questionId);
        Question _questionExists = mapper.map(questionExists, Question.class);
        if(_questionExists == null)
            throw new SurveysException("Question not found");
        if(_questionExists.getSurvey().getId()!=surveyId)
            throw new SurveysException("Question not found");
        QuestionVTO question = mapper.map(_questionExists, QuestionVTO.class);
        return (question);

    }

//    public List<QuestionVTO> getQuestions(Date createdOn, Integer pageNum, Integer pageSize) {
//        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdOn").descending());
//        List<Question> questionList = repo.findAllByCreatedOn(createdOn,pageable);
//        List<QuestionVTO> questionVTOS = new ArrayList<>();
//        for(Question question:questionList){
//            QuestionVTO questionVto = mapper.map(question, QuestionVTO.class);
//            questionVTOS.add(questionVto);
//        }
//        return (questionVTOS);
//
//    }

    public QuestionFilterVTO getAllQuestionsFilters (QuestionFilterDTO questionFilterDTO){
        Page<Question> questionsPage = questionCriteriaRepository.findAllQuestionsWithFilter(questionFilterDTO);
        Integer count = questionsPage.getNumberOfElements();
        List<QuestionVTO> questionVTOS = questionsPage.getContent().stream()
                .map(user -> mapper.map(user, QuestionVTO.class))
                .collect(Collectors.toList());
        QuestionFilterVTO questionFilterVTO = new QuestionFilterVTO();
        questionFilterVTO.setQuestionVTOS(questionVTOS);
        questionFilterVTO.setCount(count);
        return questionFilterVTO;
    }


}
