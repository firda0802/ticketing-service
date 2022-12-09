package com.binar.tix.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "orders")
public class Orders implements Serializable {

    @Id
    @Column(name = "invoice_no")
    private String invoiceNo;

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

    @Column(name = "qrcode_url")
    private String qrCodeUrl;

    @Column(name = "amount")
    private Integer amount;

    @CreationTimestamp
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "schedule_id")
    private int scheduleId;

    @ManyToOne
    @JoinColumn(name = "schedule_id", insertable = false, updatable = false)
    private Schedule schedule;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_no")
    private Set<OrdersDetails> ordersDetails;

}
