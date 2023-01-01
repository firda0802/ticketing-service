package com.binar.tix.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "passenger_type")
public class PassengerType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passengertype_id")
    private Integer passengertypeId;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @Column(name = "position")
    private int position;

    public PassengerType(String type, String description, int position) {
        this.type = type;
        this.description = description;
        this.position = position;
    }
}
