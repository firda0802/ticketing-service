package com.binar.tix.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "orders")
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "orders_id", nullable = false)
    private Long orders_id;

    @Column(name = "user_id")
    private int userId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users users;

    @Column(name = "payment_id")
    private int paymentId;

    @ManyToOne
    @JoinColumn(name = "payment_id", insertable = false, updatable = false)
    private Payment payment;


    @Column(name = "title")
    private String title;

    @Column(name = "booking_by")
    private String bookingBy;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "schedule_id")
    private int scheduleId;

    @ManyToOne
    @JoinColumn(name = "schedule_id", insertable = false, updatable = false)
    private Schedule schedule;

}
