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
@Table(name = "airplane")

public class Airplane implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "airplane_seq")
    @SequenceGenerator(name = "airplane_seq", sequenceName = "id_airplane_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_airplane", nullable = false)
    private Integer id_airplane;

    @Column(name = "type")
    private String type;

    @Column(name = "luggage_capacity")
    private int luggage_capacity;

    @Column(name = "id_airport")
    private int airportId;

    @ManyToOne
    @JoinColumn(name = "id_airport", insertable = false, updatable = false)
    private Airport airport;

}
