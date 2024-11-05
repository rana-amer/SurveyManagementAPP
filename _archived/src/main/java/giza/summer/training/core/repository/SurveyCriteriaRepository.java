package giza.summer.training.core.repository;

import giza.summer.training.model.entity.Survey;
import giza.summer.training.model.entity.SurveyPage;
import giza.summer.training.model.entity.Survey_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class SurveyCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public SurveyCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }
        public Page<Survey> getAllSurveys(SurveyPage surveyPage){
            CriteriaQuery<Survey> criteriaQuery = criteriaBuilder.createQuery(Survey.class);
            Root<Survey> surveyRoot = criteriaQuery.from(Survey.class);
            Predicate predicate = getPredicate(surveyPage,surveyRoot);
            criteriaQuery.where(predicate);
            setOrder(surveyPage,criteriaQuery,surveyRoot);

            TypedQuery<Survey> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setFirstResult(surveyPage.getPageNumber() * surveyPage.getPageSize());
            typedQuery.setMaxResults(surveyPage.getPageSize());

            Pageable pageable = getPageable(surveyPage);

            long usersCount = getSurveysCount(predicate);

            return new PageImpl<>(typedQuery.getResultList(),pageable,usersCount);
    }

    private Predicate getPredicate(SurveyPage surveyPage, Root<Survey> surveyRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(surveyPage.getTitle())){
            predicates.add(
                    criteriaBuilder.like(surveyRoot.get(Survey_.TITLE),
                            "%" + surveyPage.getTitle() + "%")
            );
        }
        if(Objects.nonNull(surveyPage.getStartDateFrom())){
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(
                            surveyRoot.get(Survey_.START_DATE),
                            surveyPage.getStartDateFrom())
            );
        }
        if(Objects.nonNull(surveyPage.getEndDateTo())){
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(
                            surveyRoot.get(Survey_.END_DATE),
                            surveyPage.getEndDateTo())
            );
        }
        if(Objects.nonNull(surveyPage.getCreatedOnFrom())&&Objects.nonNull(surveyPage.getCreatedOnTo())){
            predicates.add(
                    criteriaBuilder.between(
                            surveyRoot.get(Survey_.CREATED_ON),
                            surveyPage.getCreatedOnFrom(),
                            surveyPage.getCreatedOnTo()
                    )
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
    private void setOrder(SurveyPage surveyPage,
                          CriteriaQuery<Survey> criteriaQuery,
                          Root<Survey> surveyRoot) {
        if(surveyPage.getSortDir().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(surveyRoot.get(surveyPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(surveyRoot.get(surveyPage.getSortBy())));
        }
    }
    private Pageable getPageable(SurveyPage surveyPage) {
        Sort sort = Sort.by(surveyPage.getSortDir(), surveyPage.getSortBy());
        return PageRequest.of(surveyPage.getPageNumber(),surveyPage.getPageSize(), sort);
    }

    private long getSurveysCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Survey> countRoot = countQuery.from(Survey.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
