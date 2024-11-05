package giza.summer.training.core.repository;

import giza.summer.training.model.entity.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    boolean existsByTitle(String title);
     Page<Survey> findAllByCreatedOn(Date createdOn, Pageable pageable);



}
