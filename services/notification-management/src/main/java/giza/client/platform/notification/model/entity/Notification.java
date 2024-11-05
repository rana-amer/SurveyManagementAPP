package giza.client.platform.notification.model.entity;


import giza.user.sync.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "notification_request_id")
    private NotificationRequest notificationRequest;

    @ManyToOne
    @JoinColumn(name = "notifier_id")
    private User notifier;

    @Basic
    @Column(name = "body")
    private String body;

    @Basic
    @Column(name = "subject")
    private String subject;



}

