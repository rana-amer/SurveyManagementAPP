package giza.client.platform.notification.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Basic
    @Column(name = "entity_id")
    private Integer entityId;

    @Basic
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Basic
    @Column(name = "actor_id")
    private Integer actorId;




}

