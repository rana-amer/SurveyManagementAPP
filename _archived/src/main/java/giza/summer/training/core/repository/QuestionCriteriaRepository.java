package giza.summer.training.core.repository;


import giza.summer.training.model.dto.QuestionFilterDTO;
import giza.summer.training.model.entity.Question;
import giza.summer.training.model.entity.Question_;
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
public class QuestionCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;


    public QuestionCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Question> findAllQuestionsWithFilter(QuestionFilterDTO questionFilterDto){
        CriteriaQuery<Question> criteriaQuery = criteriaBuilder.createQuery(Question.class);
        Root<Question> questionRoot = criteriaQuery.from(Question.class);
        Predicate predicate = getPredicate(questionFilterDto, questionRoot);

        criteriaQuery.where(predicate);
        setOrder(questionFilterDto, criteriaQuery, questionRoot);

        TypedQuery<Question> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(questionFilterDto.getPageNumber() * questionFilterDto.getPageSize());
        typedQuery.setMaxResults(questionFilterDto.getPageSize());
        Pageable pageable = getPageable(questionFilterDto);
        long questionCount = 0;//getQuestionCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, questionCount);



    }

    private Predicate getPredicate(QuestionFilterDTO questionFilterDto,
                                   Root<Question> employeeRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(questionFilterDto.getQuestionOrder())){
            predicates.add(
                    criteriaBuilder.equal(employeeRoot.get(Question_.QUESTION_ORDER), questionFilterDto.getQuestionOrder())
            );
        }
        if(Objects.nonNull(questionFilterDto.getCreatedOnFrom())) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(employeeRoot.get(Question_.CREATED_ON), questionFilterDto.getCreatedOnFrom())
            );
        }
        if(Objects.nonNull(questionFilterDto.getCreatedOnTo())) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(employeeRoot.get(Question_.CREATED_ON), questionFilterDto.getCreatedOnTo())
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }


    private void setOrder(QuestionFilterDTO questionFilterDto,
                          CriteriaQuery<Question> criteriaQuery,
                          Root<Question> questionRoot) {
        if(questionFilterDto.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(questionRoot.get(questionFilterDto.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(questionRoot.get(questionFilterDto.getSortBy())));
        }
    }

    private Pageable getPageable(QuestionFilterDTO questionFilterDto) {
        Sort sort = Sort.by(questionFilterDto.getSortDirection(), questionFilterDto.getSortBy());
        return PageRequest.of(questionFilterDto.getPageNumber(),questionFilterDto.getPageSize(), sort);
    }

    private long getQuestionCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Question> countRoot = countQuery.from(Question.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
