package giza.summer.training.controller;


import giza.summer.training.api.service.IQuestionService;
import giza.summer.training.model.dto.QuestionDTO;
import giza.summer.training.model.dto.QuestionFilterDTO;
import giza.summer.training.model.vto.QuestionFilterVTO;
import giza.summer.training.model.vto.QuestionVTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/surveys")
public class QuestionController {
    private final IQuestionService questionService;

    public QuestionController(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/{surveyId}/questions")
    public ResponseEntity<?> createQuestion (@PathVariable Long surveyId, @RequestBody QuestionDTO question)
    {
        questionService.createQuestion(question,surveyId);
        return ResponseEntity.ok("Question Created");
    }

    @PutMapping("/{surveyId}/questions/{questionId}")
    public ResponseEntity<?> updateQuestionById(@PathVariable Long surveyId, @PathVariable Long questionId, @RequestBody QuestionDTO question)
    {
        questionService.updateQuestionById(question,surveyId,questionId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{surveyId}/questions/{questionId}")
    public ResponseEntity<?> deleteQuestionById(@PathVariable Long questionId, @PathVariable Long surveyId) {

        questionService.deleteQuestionById(surveyId,questionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{surveyId}/questions/{questionId}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long questionId, @PathVariable Long surveyId) {

        QuestionVTO question = questionService.getQuestionById(surveyId,questionId);
        return ResponseEntity.ok(question);
    }
//    @GetMapping("/questions")
//    public ResponseEntity<?> getQuestions(
//            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:SS")
//            @RequestParam Date createdOn
//            , @RequestParam Integer pageNum,
//                                          @RequestParam Integer pageSize) {
//
//        List<QuestionVTO> questionVTOs = questionService.getQuestions(createdOn, pageNum, pageSize);
//        return ResponseEntity.ok(questionVTOs);
//    }

    @GetMapping("/questions/filters")
    public ResponseEntity<?> getAllQuestionsFilters(QuestionFilterDTO questionFilterDTO) {

        QuestionFilterVTO questionFilterVTO = questionService.getAllQuestionsFilters(questionFilterDTO);
        return ResponseEntity.ok(questionFilterVTO);
    }
}
