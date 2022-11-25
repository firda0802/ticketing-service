package com.binar.tix.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "seats")
public class Seats implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seats_id")
    private Integer seatsId;

    @Column(name = "seats_number")
    private String seatsNumber;

    @Column(name = "seats_group")
    private Integer seatsGroup;

    @Column(name = "class_id")
    private Integer classId;

    @Column(name = "airplanes_id")
    private Integer airplanesId;
}
