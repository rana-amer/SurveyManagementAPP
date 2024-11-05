package giza.user.sync.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "id")
    private Long Id;

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "full_name")
    private String fullName;

    @Basic
    @Column(name = "gender")
    private String gender;

    @Basic
    @Column(name = "is_active")
    private Boolean isActive;

    @Basic
    @Column(name = "created_on")
    private Date createdOn;

    @Basic
    @Column(name = "mobile")
    private String mobile;

    @Basic
    @Column(name = "email")
    private String email;


}
