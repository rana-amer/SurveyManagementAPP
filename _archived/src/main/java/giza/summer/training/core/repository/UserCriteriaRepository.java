package giza.summer.training.core.repository;

import giza.summer.training.model.UserListFilter;
import giza.summer.training.model.entity.User;
import giza.summer.training.model.entity.User_;
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
public class UserCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public UserCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<User> findAllWithFilters(UserListFilter userListFilter){
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        Predicate predicate = getPredicate(userListFilter,userRoot);
        criteriaQuery.where(predicate);
        setOrder(userListFilter,criteriaQuery,userRoot);

        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(userListFilter.getPageNumber() * userListFilter.getPageSize());
        typedQuery.setMaxResults(userListFilter.getPageSize());

        Pageable pageable = getPageable(userListFilter);

        long usersCount = getUsersCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(),pageable,usersCount);
    }

    private Predicate getPredicate(UserListFilter userSearchCriteria, Root<User> userRoot){
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(userSearchCriteria.getFullName()))
            predicates.add(criteriaBuilder.like(userRoot.get(User_.FULL_NAME),"%" + userSearchCriteria.getFullName() + "%"));
        if(Objects.nonNull(userSearchCriteria.getUsername()))
            predicates.add(criteriaBuilder.like(userRoot.get(User_.USERNAME),"%" + userSearchCriteria.getUsername() + "%"));
        if(Objects.nonNull(userSearchCriteria.getCreatedOnFrom()))
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(userRoot.get(User_.CREATED_ON),userSearchCriteria.getCreatedOnFrom()));
        if(Objects.nonNull(userSearchCriteria.getCreatedOnTo()))
            predicates.add(criteriaBuilder.lessThanOrEqualTo(userRoot.get(User_.CREATED_ON),userSearchCriteria.getCreatedOnTo()));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
    private void setOrder(UserListFilter userListFilter, CriteriaQuery<User> criteriaQuery, Root<User> userRoot){
        if(userListFilter.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(userRoot.get(userListFilter.getSortBy())));
        }else {
            criteriaQuery.orderBy(criteriaBuilder.desc(userRoot.get(userListFilter.getSortBy())));
        }

    }
    private Pageable getPageable(UserListFilter userListFilter){
        Sort sort = Sort.by(userListFilter.getSortDirection(), userListFilter.getSortBy());
        return PageRequest.of(userListFilter.getPageNumber(), userListFilter.getPageSize(),sort);
    }
    private long getUsersCount(Predicate predicate){
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();

    }
}
