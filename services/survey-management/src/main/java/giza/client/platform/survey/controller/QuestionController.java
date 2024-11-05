package giza.client.platform.survey.controller;


import giza.client.platform.survey.api.service.IQuestionService;
import giza.client.platform.survey.model.dto.QuestionDTO;
import giza.client.platform.survey.model.dto.SurveyAnswersDTO;
import giza.client.platform.survey.model.dto.SurveyQuestionItemDTO;
import giza.client.platform.survey.model.entity.Question;
import giza.client.platform.survey.model.vto.QuestionVTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/surveys")
public class QuestionController {
    private final IQuestionService questionService;

    public QuestionController(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/{surveyId}/questions")
    public ResponseEntity<?> createQuestion (@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable Long surveyId, @RequestBody QuestionDTO question)
    {
        questionService.createQuestion(question,surveyId);
        return ResponseEntity.ok("Question Created");
    }

    @PutMapping("/{surveyId}/questions/{questionId}")
    public ResponseEntity<?> updateQuestionById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@PathVariable Long surveyId, @PathVariable Long questionId, @RequestBody QuestionDTO question)
    {
        questionService.updateQuestionById(question,surveyId,questionId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{surveyId}/questions/{questionId}")
    public ResponseEntity<?> deleteQuestionById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@PathVariable Long questionId, @PathVariable Long surveyId) {

        questionService.deleteQuestionById(surveyId,questionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{surveyId}/questions/{questionId}")
    public ResponseEntity<?> getQuestionById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@PathVariable Long questionId, @PathVariable Long surveyId) {

        QuestionVTO question = questionService.getQuestionById(surveyId,questionId);
        return ResponseEntity.ok(question);
    }
    @GetMapping("/bySurveyId/{surveyId}")
    public ResponseEntity<?> getSurveyQuestions(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@PathVariable Long surveyId){
        List<SurveyQuestionItemDTO> surveyQuestions=questionService.getSurveyQuestions(surveyId);
        return ResponseEntity.ok(surveyQuestions);
    }

    @PutMapping("/submitAnswers/{surveyDistributionId}")
    public ResponseEntity<?> submitWithAnswers(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@PathVariable Long surveyDistributionId, @RequestBody List<SurveyAnswersDTO> surveyAnswers){
        questionService.submitWithAnswers(surveyDistributionId,surveyAnswers);
        return ResponseEntity.ok("submitted");
    }

}
