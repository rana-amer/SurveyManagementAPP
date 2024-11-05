package giza.client.platform.survey.core.service;

import giza.client.platform.survey.api.service.ISurveyService;
import giza.client.platform.survey.core.repository.SurveyAnswersRepository;
import giza.client.platform.survey.core.repository.SurveyDistributionRepository;
import giza.client.platform.survey.core.repository.SurveyRepository;
import giza.client.platform.survey.model.dto.SurveyAnswerInfoDTO;
import giza.client.platform.survey.model.dto.SurveyAnswersDTO;
import giza.client.platform.survey.model.dto.SurveyDto;
import giza.client.platform.survey.model.entity.Survey;
import giza.client.platform.survey.model.entity.SurveyAnswers;
import giza.client.platform.survey.model.entity.SurveyDistribution;
import giza.client.platform.survey.model.enums.SurveyStatuses;
import giza.client.platform.survey.model.exceptions.SurveysException;
import giza.client.platform.survey.model.vto.SurveyAllAnswersVTO;
import giza.client.platform.survey.model.vto.SurveyVTO;
import giza.notification.sender.model.dto.NotificationDTO;
import giza.notification.sender.service.SendNotification;
import org.modelmapper.ModelMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.LocalTime.now;

@Service
public class SurveyService implements ISurveyService {
    private final SurveyRepository repo;
    private final SurveyDistributionRepository surveyDistributionRepository;
    private final JmsTemplate jmsTemplate;
    private final ModelMapper mapper;
    private final SurveyAnswersRepository surveyAnswersRepository;

    private final SendNotification sendNotification;



    public SurveyService(SurveyRepository repo, SurveyDistributionRepository surveyDistributionRepository, JmsTemplate jmsTemplate, ModelMapper mapper, SurveyAnswersRepository surveyAnswersRepository, SendNotification sendNotification) {
        this.repo = repo;
        this.surveyDistributionRepository = surveyDistributionRepository;

        this.jmsTemplate = jmsTemplate;
        this.mapper = mapper;
        this.surveyAnswersRepository = surveyAnswersRepository;
        this.sendNotification = sendNotification;
    }

    public Long createSurvey(SurveyDto survey) {
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
        Survey survey1 = mapper.map(survey, Survey.class);
        survey1.setCreatedOn(LocalDateTime.now());
        survey1.setSurveyStatus(SurveyStatuses.New.id());
        repo.save(survey1);
 //sss
        System.out.println("create with not");
        Map<String, String> m = new HashMap<String, String>();

        // Initialize frequency table from command line
        m.put("deadline","9/25/2023");
        NotificationDTO notificationDTO = new NotificationDTO(
                1,
                1,
                new Date(),
                1,
                Arrays.asList(2),
                m

        );

        // Add a key-value pair to the parameters map
        notificationDTO.getParameters().put("name", "nada");
        sendNotification.sendNotification(notificationDTO);
        return survey1.getId();
    }
    public void updateSurvey(SurveyDto obj, Long id) {
        Optional<Survey> optionalSurvey = repo.findById(id);

        if (optionalSurvey.isEmpty()) {
            throw new SurveysException("Survey not found with ID: " + id);
        }
        Survey survey = optionalSurvey.get();
        if (survey.getEndDate() != null) {
            LocalDateTime endDateTime = survey.getEndDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            if (!endDateTime.isAfter(LocalDateTime.now())) {
                throw new SurveysException("End date should be after current time");
            }
        }


        mapper.map(obj, survey);
        repo.save(survey);
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
        //jmsTemplate.convertAndSend("giza.create.hey","survey");
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
    public void distributeSurvey(Long surveyId, Long[] recipients) {
        Date now = new Date(); // Get the current timestamp
        System.out.println("henaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        NotificationDTO notificationDTO = new NotificationDTO(
                1,
                1,
                new Date(),
                1,
                Arrays.asList(2, 3, 4),
                new HashMap<>()
        );
        sendNotification.sendNotification(notificationDTO);


        for (Long recipientId : recipients) {
            SurveyDistribution surveyDistribution = new SurveyDistribution();


            surveyDistribution.setSurveyId(surveyId);
            surveyDistribution.setUserId(recipientId);


            surveyDistribution.setCreatedOn(now);
            surveyDistribution.setSubmitOn(null);


            surveyDistributionRepository.save(surveyDistribution);
        }
    }

    @Override
    public void submitSurvey(Long surveyId, Long userId) {
        SurveyDistribution surveyDistribution = surveyDistributionRepository.findBySurveyIdAndUserId(surveyId, userId);
        if (surveyDistribution != null) {
            surveyDistribution.setSubmitOn(new Date());


            surveyDistributionRepository.save(surveyDistribution);
        } else {
            throw new SurveysException("Survey distribution not found");
        }

    }

    @Override
    public List<SurveyAllAnswersVTO> getSurveyAnswers(Long surveyId) {
        List<SurveyDistribution> surveyDistributions = surveyDistributionRepository.findBySurveyId(surveyId);
        List<Long> surveyDistributionIds = surveyDistributions.stream()
                .map(SurveyDistribution::getId)
                .collect(Collectors.toList());
        List<SurveyAnswers> surveyAnswersList = surveyAnswersRepository.findBySurveyDistributionIdIn(surveyDistributionIds);

        Map<Long, List<SurveyAnswerInfoDTO>> surveyAnswersMap = surveyAnswersList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.groupingBy(SurveyAnswerInfoDTO::getSurveyDistributionId));

        List<SurveyAllAnswersVTO> result = surveyAnswersMap.entrySet().stream()
                .map(entry -> {
                    List<SurveyAnswersDTO> answersDTOList = entry.getValue().stream()
                            .map(this::mapToSurveyAnswersDTO)
                            .collect(Collectors.toList());

                    return SurveyAllAnswersVTO.builder()
                            .surveyAnswersDTOList(answersDTOList)
                            .build();
                })
                .collect(Collectors.toList());
return result;
    }
    public SurveyAnswerInfoDTO mapToDTO(SurveyAnswers surveyAnswers) {
        return SurveyAnswerInfoDTO.builder()
                .SurveyDistributionId(surveyAnswers.getSurveyDistribution().getId())
                .value(surveyAnswers.getValue())
                .build();
    }
    private SurveyAnswersDTO mapToSurveyAnswersDTO(SurveyAnswerInfoDTO infoDTO) {
        SurveyAnswersDTO surveyAnswersDTO = new SurveyAnswersDTO();
        surveyAnswersDTO.setValue(infoDTO.getValue());
        return surveyAnswersDTO;
    }


    @Override
//   @Scheduled(fixedRate = 10000)
    public void distribute() throws ParseException {
       Date date = new Date();
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       String formattedDateStr = dateFormat.format(date);
       System.out.println("Formatted Date in String format: "+formattedDateStr);
       Date x = dateFormat.parse(formattedDateStr);
       List<Survey> surveys = repo.findByStartDate(x);
        for (Survey survey : surveys) {
            System.out.println("ssss");
            Long surveyId = survey.getId();
            List<Long> userIds = List.of(1L, 2L);

            distributeSurvey(surveyId, userIds);
        }
    }
    public void distributeSurvey(Long surveyId, List<Long> userIds) {
        System.out.println("ddddddddddddddddddddddddddd");
        Date date = new Date();
        NotificationDTO notificationDTO = new NotificationDTO(
                1,
                1,
                new Date(),
                1,
                Arrays.asList(2, 3, 4),
                new HashMap<>()
        );
        sendNotification.sendNotification(notificationDTO);
        for (Long userId : userIds) {
            System.out.println(userId);
            SurveyDistribution surveyDistribution = new SurveyDistribution();

            surveyDistribution.setSurveyId(surveyId);
            surveyDistribution.setUserId(userId);
            surveyDistribution.setCreatedOn(date);
            surveyDistribution.setSubmitOn(null);
            //surveyDistributionRepository.save(surveyDistribution);
        }
    }
}