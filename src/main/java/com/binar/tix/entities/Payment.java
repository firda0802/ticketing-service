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
@Table(name = "payment")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "payment_id", nullable = false)
    private Integer paymentId;

    @Column(name ="payment_method")
    private String paymentMethod;

    @Column(name = "status")
    private Boolean status;

    public Payment(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}

