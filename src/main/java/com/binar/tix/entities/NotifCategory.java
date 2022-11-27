package com.binar.tix.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "notif_category")
public class NotifCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notif_category_seq")
    @SequenceGenerator(name = "notif_category_seq", sequenceName = "id_notif_category_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "notif_category_id", nullable = false)
    private Integer notificationCategoryId;

    @Column(name = "notif_category")
    private String notifcategoryName;

    public NotifCategory() {
    }

    public NotifCategory(String notifcategory) {
        this.notifcategoryName = notifcategory;
    }
}
