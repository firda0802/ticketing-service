package com.binar.tix.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "orders_details")
public class OrdersDetails implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_details_seq")
    @SequenceGenerator(name = "order_details_seq", sequenceName = "id_order_details_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_order_details", nullable = false)
    private Integer idOrderDetails;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "title")
    private String title;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "seats_id")
    private Integer seatsId;

    @ManyToOne
    @JoinColumn(name = "seats_id", insertable = false, updatable = false)
    private Seats seats;

    @Column(name = "passenger_type")
    private Integer passengerType;

    @ManyToOne
    @JoinColumn(name = "passenger_type", insertable = false, updatable = false)
    private PassengerType passenger;

    @Column(name = "citizenship_id")
    private Integer citizenshipId;

    @ManyToOne
    @JoinColumn(name = "citizenship_id", insertable = false, updatable = false)
    private Citizenship citizenship;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "issuing_country")
    private String issuingCountry;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;
}
