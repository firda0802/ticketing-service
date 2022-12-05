package com.binar.tix.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "payment_id", nullable = false)
    private Integer payment_id;

    @Column(name = "id_admin")
    private int id_admin;

    @ManyToOne
    @JoinColumn(name = "id_admin", insertable = false, updatable = false)
    private Admin admin;

    @Column(name ="payment_method")
    private String paymentMethod;

}

