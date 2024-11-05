package giza.summer.training.core.service;

import giza.summer.training.api.service.ISurveyService;
import giza.summer.training.core.repository.SurveyCriteriaRepository;
import giza.summer.training.core.repository.SurveyRepository;
import giza.summer.training.model.dto.SurveyDTO;
import giza.summer.training.model.entity.Survey;
import giza.summer.training.model.entity.SurveyPage;
import giza.summer.training.model.enums.SurveyStatuses;
import giza.summer.training.model.exceptions.SurveysException;
import giza.summer.training.model.vto.SurveyFilterVTO;
import giza.summer.training.model.vto.SurveyVTO;
import giza.summer.training.model.vto.UserListFilterVTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SurveyService implements ISurveyService {
    private final SurveyRepository repo;
    private final ModelMapper mapper;
    private final SurveyCriteriaRepository criteriaRepository;


    public SurveyService(SurveyRepository repo, ModelMapper mapper, SurveyCriteriaRepository criteriaRepository) {
        this.repo = repo;
        this.mapper = mapper;
        this.criteriaRepository = criteriaRepository;
    }

    public Long createSurvey(SurveyDTO survey) {
        if (repo.existsByTitle(survey.getTitle())) {
            throw new SurveysException("A survey with this title already exists");
        }
        if (survey.getEndDate() != null) {
            LocalDateTime endDateTime = survey.getEndDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            if (!endDateTime.isAfter(LocalDateTime.now())) {
                throw new SurveysException("End date should be after current time");
            }
        }
        Survey surv = mapper.map(survey, Survey.class);
        surv.setCreatedOn(LocalDateTime.now());
        surv.setSurveyStatus(SurveyStatuses.New.id());
        repo.save(surv);

        return surv.getId();
    }

    @Override
    public void updateSurvey(SurveyDTO obj, Long id) {
        Optional<Survey> optionalSurvey = repo.findById(id);

        if (optionalSurvey.isEmpty()) {
            throw new SurveysException("Survey not found with ID: " + id);
        }

        Survey existingSurvey = optionalSurvey.get();
        mapper.map(obj, existingSurvey);
        repo.save(existingSurvey);

    }


    @Override
    public void closeSurvey(Long id) {
        Optional<Survey> optionalSurvey = repo.findById(id);

        if (optionalSurvey.isEmpty()) {
            throw new SurveysException("Survey not found with ID: " + id);
        }

        Survey survey = optionalSurvey.get();
        survey.setSurveyStatus(SurveyStatuses.Closed.id());
        survey.setClosedOn(new Date());
        repo.save(survey);
    }

    @Override
    public void deleteSurvey(Long id) {
        Optional<Survey> optionalSurvey = repo.findById(id);

        if (optionalSurvey.isEmpty()) {
            throw new SurveysException("Survey not found with ID: " + id);
        }

        repo.deleteById(id);
    }

    public SurveyVTO getSurvey(Long id) {
        Optional<Survey> optionalSurvey = repo.findById(id);

        if (optionalSurvey.isEmpty()) {
            throw new SurveysException("Survey not found with ID: " + id);
        }

        Survey survey = optionalSurvey.get();
        SurveyVTO surveyVTO = mapper.map(survey, SurveyVTO.class);
        return surveyVTO;

    }


    @Override
    public List<SurveyVTO> getSurveys(int page, int size, Date createdOn, String orderBy,
                                      String orderDir) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdOn"), Sort.Order.asc(orderBy)));
        if (createdOn != null) {
            Page<Survey> surveyPage = repo.findAllByCreatedOn(createdOn
                    , pageable);
            List<SurveyVTO> surveyVTOS = surveyPage.getContent().stream()
                    .map(survey -> mapper.map(survey, SurveyVTO.class))
                    .collect(Collectors.toList());
            return surveyVTOS;

        }
        else{
            Page<Survey> surveyPage = repo.findAll(pageable);
            List<SurveyVTO> surveyVTOS = surveyPage.getContent().stream()
                    .map(survey -> mapper.map(survey, SurveyVTO.class))
                    .collect(Collectors.toList());
            return surveyVTOS;


        }

    }

    @Override
    public SurveyFilterVTO getSurveysWithFilter(SurveyPage surveyPage) {
        Page<Survey> surveysPage=criteriaRepository.getAllSurveys(surveyPage);
        List<SurveyVTO> surveyVTOS = surveysPage.getContent().stream()
                .map(survey -> mapper.map(survey, SurveyVTO.class))
                .collect(Collectors.toList());
        SurveyFilterVTO surveyFilterVTO = new SurveyFilterVTO();
        surveyFilterVTO.setSurveyVTOS(surveyVTOS);
        Integer count = surveysPage.getNumberOfElements();
        surveyFilterVTO.setCount(count);
        return surveyFilterVTO;
    }


}
