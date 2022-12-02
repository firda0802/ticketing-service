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
@Table(name = "pricing")
public class Pricing implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pricing_seq")
    @SequenceGenerator(name = "pricing_seq", sequenceName = "pricing_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "pricing_id")
    private Integer pricingId;

    @Column(name = "class_id")
    private Integer classId;

    @ManyToOne
    @JoinColumn(name = "class_id", insertable = false, updatable = false)
    private ClassSeats classSeats;

    @Column(name = "price")
    private int price;



    public Pricing(int price) {
        this.price = price;
    }
}
