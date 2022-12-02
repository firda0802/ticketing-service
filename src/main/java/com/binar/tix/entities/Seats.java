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
    private char seatsGroup;

    @Column(name = "position")
    private Integer positions;
    @Column(name = "class_id")
    private Integer classId;

    @Column(name = "airplane_id")
    private Integer airplanesId;

    @ManyToOne
    @JoinColumn(name = "class_id", insertable = false, updatable = false)
    private ClassSeats classSeats;

    public Seats() {
    }

    public Seats(String seatsNumber, char seatsGroup, Integer classId, Integer positions) {
        this.seatsNumber = seatsNumber;
        this.seatsGroup = seatsGroup;
        this.classId = classId;
        this.positions = positions;
    }
}
