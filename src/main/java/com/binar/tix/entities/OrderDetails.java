package com.binar.tix.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "orderDetails")
public class OrderDetails  implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_details_seq")
    @SequenceGenerator(name = "order_details_seq", sequenceName = "id_order_details_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_order_details", nullable = false)
    private Integer id_order_details;

    @Column(name = "seats_id")
    private int seats_id;

    @ManyToOne
    @JoinColumn(name = "seats_id", insertable = false, updatable = false)
    private Seats seats;

    @Column(name = "invoice_no")
    private long invoice_no;

    @ManyToOne
    @JoinColumn(name = "invoice_no_id", insertable = false, updatable = false)
    private OrderDetails orderDetails;

    @Column(name = "title")
    private String title;

    @Column(name = "fullname")
    private String fullName;

}
