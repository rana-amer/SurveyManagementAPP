package giza.client.platform.survey.core.service;


import giza.client.platform.survey.api.service.IQuestionService;
import giza.client.platform.survey.core.repository.*;
import giza.client.platform.survey.model.dto.QuestionChoiceDTO;
import giza.client.platform.survey.model.dto.QuestionDTO;
import giza.client.platform.survey.model.dto.SurveyAnswersDTO;
import giza.client.platform.survey.model.dto.SurveyQuestionItemDTO;
import giza.client.platform.survey.model.entity.*;
import giza.client.platform.survey.model.exceptions.SurveysException;
import giza.client.platform.survey.model.vto.QuestionVTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionRepository repo;

    private final ModelMapper mapper;
    private final QuestionChoiceRepository questionChoiceRepository;

    private final SurveyAnswersRepository surveyAnswersRepository;

    private final SurveyDistributionRepository surveyDistributionRepository;

    @Override
    public void createQuestion(QuestionDTO questionDto, Long surveyId) {
        List<QuestionChoiceDTO> x=questionDto.getChoices();
        Question question = mapper.map(questionDto, Question.class);

        Survey survey = new Survey();
        survey.setId(surveyId);
        QuestionType questionType = new QuestionType();
        questionType.setId(questionDto.getQuestionTypeId());
        question.setSurvey(survey);
        question.setQuestionType(questionType);
        //question.setCreatedOn(new Date());
        if(x!=null){
            for (QuestionChoiceDTO choiceDTO : x) {
                System.out.println(choiceDTO.getChoice());
                System.out.println(choiceDTO);
                QuestionChoice choice = new QuestionChoice();
                choice.setChoice(choiceDTO.getChoice());
                choice.setChoiceOrder(choiceDTO.getOrder());
                choice.setQuestion(question);
                questionChoiceRepository.save(choice);
            }
        }

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

    @Override
    public List<SurveyQuestionItemDTO> getSurveyQuestions(Long surveyId) {
        List<Question> q = repo.findBySurveyIdOrderByQuestionOrder(surveyId);

        List<SurveyQuestionItemDTO> result = q.stream()
                .map(question -> {
                    SurveyQuestionItemDTO dto = new SurveyQuestionItemDTO();
                    dto.setQuestionText(question.getQuestionText());
                    dto.setIsMandatory(question.getIsMandatory());
                    dto.setQuestionOrder(question.getQuestionOrder());
                    dto.setIsSelectingMultiChoiceAllowed(question.getIsSelectingMultiChoiceAllowed());
                    if(question.getQuestionType().getId()==1){
                     List<QuestionChoice> qc=questionChoiceRepository.findByQuestionId(question.getId());
                        List<QuestionChoiceDTO> choiceDTOs = qc.stream()
                                .map(this::mapChoiceToDTO)
                                .collect(Collectors.toList());

                        dto.setChoices(choiceDTOs);
                    }


                    return dto;
                })
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public void submitWithAnswers(Long surveyDistributionId, List<SurveyAnswersDTO> surveyAnswers) {
        Optional<SurveyDistribution> surveyDistribution=surveyDistributionRepository.findById(surveyDistributionId);
        if(surveyDistribution.isPresent()){
            SurveyDistribution surveyD = surveyDistribution.get();


            surveyD.setSubmitOn(new Date());

            surveyDistributionRepository.save(surveyD);
            for (SurveyAnswersDTO answersDTO : surveyAnswers) {
                SurveyAnswers surveyAnswer = new SurveyAnswers();
                surveyAnswer.setSurveyDistribution(surveyD);
                surveyAnswer.setValue(answersDTO.getValue());
                surveyAnswersRepository.save(surveyAnswer);
            }
        }


    }

    private QuestionChoiceDTO mapChoiceToDTO(QuestionChoice choice) {
        QuestionChoiceDTO dto = new QuestionChoiceDTO();
        dto.setChoice(choice.getChoice());
        dto.setOrder(choice.getChoiceOrder());
        return dto;
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

//    public QuestionFilterVTO getAllQuestionsFilters (QuestionFilterDTO questionFilterDTO){
//        Page<Question> questionsPage = questionCriteriaRepository.findAllQuestionsWithFilter(questionFilterDTO);
//        Integer count = questionsPage.getNumberOfElements();
//        List<QuestionVTO> questionVTOS = questionsPage.getContent().stream()
//                .map(user -> mapper.map(user, QuestionVTO.class))
//                .collect(Collectors.toList());
//        QuestionFilterVTO questionFilterVTO = new QuestionFilterVTO();
//        questionFilterVTO.setQuestionVTOS(questionVTOS);
//        questionFilterVTO.setCount(count);
//        return questionFilterVTO;
//    }


}
