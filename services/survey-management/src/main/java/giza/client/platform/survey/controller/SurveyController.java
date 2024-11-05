package giza.client.platform.survey.controller;

import giza.client.platform.survey.api.service.ISurveyService;
import giza.client.platform.survey.model.dto.SurveyDto;
import giza.client.platform.survey.model.vto.SurveyAllAnswersVTO;
import giza.client.platform.survey.model.vto.SurveyVTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/survey")
public class SurveyController {
    private final
    ISurveyService surveyService;

    public SurveyController(ISurveyService surveyService) {
        this.surveyService = surveyService;
    }
    @PostMapping()
    @Secured({"admin","manager"})
    public ResponseEntity<?> createSurvey(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@RequestBody SurveyDto survey) {
        Long id = surveyService.createSurvey(survey);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{surveyId}")
    public ResponseEntity<?> updateSurvey(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@RequestBody SurveyDto survey, @PathVariable Long surveyId) {
        surveyService.updateSurvey(survey, surveyId);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/close/{surveyId}")
    public ResponseEntity<?> closeSurvey(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@PathVariable Long surveyId) {
        surveyService.closeSurvey(surveyId);

        return ResponseEntity.status(204).build();

    }
    @DeleteMapping("/{surveyId}")
    public ResponseEntity<?> deleteSurvey(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@PathVariable Long surveyId) {

        surveyService.deleteSurvey(surveyId);
        return ResponseEntity.status(204).build();

    }
    @GetMapping("/{surveyId}")
    public ResponseEntity<?> getSurvey(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization ,@PathVariable Long surveyId) {
        SurveyVTO surveyVTO = surveyService.getSurvey(surveyId);
        return ResponseEntity.status(200).body(surveyVTO);
    }
    @PostMapping("/distribute/{surveyId}")
    public ResponseEntity<?> distributeSurvey(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@PathVariable Long surveyId,@RequestBody Long[] recipients) {
        surveyService.distributeSurvey(surveyId,recipients);
        return ResponseEntity.status(204).build();

    }
    @PutMapping("/submit/{surveyId}/{userId}")
    public ResponseEntity<?> submitSurvey(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@PathVariable Long surveyId,@PathVariable Long userId) {
        surveyService.submitSurvey(surveyId,userId);
        return ResponseEntity.status(204).build();

    }
    @PostMapping("/distribute")
    public ResponseEntity<?> distribute() throws ParseException {
        surveyService.distribute();
        return ResponseEntity.status(204).build();

    }
    @GetMapping("/getAnswers/{surveyId}")
    public ResponseEntity<?> getSurveyAnswers(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,@PathVariable Long surveyId){
        List<SurveyAllAnswersVTO> surveyAllAnswersVTOList=surveyService.getSurveyAnswers(surveyId);
        return ResponseEntity.ok(surveyAllAnswersVTOList);
    }




}