package giza.summer.training.core.repository;

import giza.summer.training.model.entity.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

   List<Question> findAllByCreatedOn(Date createdOn, Pageable pageable);
}
