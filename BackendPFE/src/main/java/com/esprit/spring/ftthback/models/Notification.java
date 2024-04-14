package com.esprit.spring.ftthback.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "notifications")
@Getter
@Setter

public class Notification {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="notification_id")
    private Long idNotification;
    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_to_id")
    private User userTo;

    @ManyToOne
    @JoinColumn(name = "user_from_id")
    private User userFrom;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type")
    private NotificationType notificationType;

    @Column(name = "delivered")
    private boolean delivered;

    @Column(name = "_read")
    private boolean read;

    // Constructors, Getters, and Setters
}