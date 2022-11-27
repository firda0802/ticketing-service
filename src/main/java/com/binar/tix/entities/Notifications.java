package com.binar.tix.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "notification")
public class Notifications implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq")
    @SequenceGenerator(name = "notification_seq", sequenceName = "id_notification_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_notification", nullable = false)
    private Integer notificationId;

    @Column(name = "notif_category_id")
    private int notificationCategoryId;

    @ManyToOne
    @JoinColumn(name = "notif_category_id", insertable = false, updatable = false)
    private NotifCategory notifcategory;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    @CreationTimestamp
    @Column(name = "cdate")
    private LocalDateTime cdate;

    @Column(name = "status")
    private Boolean status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users users;

}
