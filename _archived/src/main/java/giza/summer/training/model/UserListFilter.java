package giza.summer.training.model;

import giza.summer.training.model.entity.User_;
import lombok.Data;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UserListFilter {

    private int pageNumber = 0;
    private int pageSize =10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = User_.CREATED_ON;
    private String fullName;
    private String username;
    private Date createdOnFrom;
    private Date createdOnTo;
}
