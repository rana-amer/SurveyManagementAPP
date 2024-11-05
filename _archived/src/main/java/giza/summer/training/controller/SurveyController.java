package giza.summer.training.controller;

import giza.summer.training.api.service.ISurveyService;
import giza.summer.training.model.dto.SurveyDTO;
import giza.summer.training.model.entity.Survey;
import giza.summer.training.model.entity.SurveyPage;
import giza.summer.training.model.vto.SurveyFilterVTO;
import giza.summer.training.model.vto.SurveyVTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/survey")
public class SurveyController {
    private final ISurveyService surveyService;

    public SurveyController(ISurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping()
    public ResponseEntity<?> createSurvey(@RequestBody SurveyDTO survey) {
        Long id = surveyService.createSurvey(survey);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{surveyId}")
    public ResponseEntity<?> updateSurvey(@RequestBody SurveyDTO survey, @PathVariable Long surveyId) {
        surveyService.updateSurvey(survey, surveyId);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/close/{surveyId}")
    public ResponseEntity<?> closeSurvey(@PathVariable Long surveyId) {
        surveyService.closeSurvey(surveyId);
        return ResponseEntity.status(204).build();

    }

    @GetMapping("/{surveyId}")
    public ResponseEntity<?> getSurvey(@PathVariable Long surveyId) {

        SurveyVTO surveyVTO = surveyService.getSurvey(surveyId);
        return ResponseEntity.status(200).body(surveyVTO);
    }

    @DeleteMapping("/{surveyId}")
    public ResponseEntity<?> deleteSurvey(@PathVariable Long surveyId) {

        surveyService.deleteSurvey(surveyId);
        return ResponseEntity.status(204).build();

    }

    @GetMapping
    public ResponseEntity<?> getSurveys(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:SS")
            @RequestParam(required = false) Date createdOn,
            @RequestParam(defaultValue = "id") String orderBy,
            @RequestParam(defaultValue = "desc") String orderDir) {
        List<SurveyVTO> surveyVTOS = surveyService.getSurveys(page, size, createdOn, orderBy, orderDir);
        return ResponseEntity.ok(surveyVTOS);
    }
    @GetMapping("/getAll")
    public ResponseEntity<?>  getSurveysWithFilter(@RequestBody SurveyPage surveyPage){
      SurveyFilterVTO surveyVTOS=surveyService.getSurveysWithFilter(surveyPage);
        return ResponseEntity.ok(surveyVTOS);

    }


}
