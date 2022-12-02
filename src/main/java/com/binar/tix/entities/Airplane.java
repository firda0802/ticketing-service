package com.binar.tix.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "airplane")

public class Airplane implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "airplane_seq")
    @SequenceGenerator(name = "airplane_seq", sequenceName = "id_airplane_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "airplane_id", nullable = false)
    private Integer airplaneId;

    @Column(name = "type")
    private String type;

    @Column(name = "luggage_capacity")
    private int luggageCapacity;

    @Column(name = "id_airport")
    private int airportId;

    @ManyToOne
    @JoinColumn(name = "id_airport", insertable = false, updatable = false)
    private Airport airport;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "airplane_id")
    private Set<Seats> seats;

    public Airplane(String type, int luggageCapacity, int airportId) {
        this.type = type;
        this.luggageCapacity = luggageCapacity;
        this.airportId = airportId;
    }
}
