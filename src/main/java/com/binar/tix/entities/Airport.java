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
@Table(name = "airport")
public class Airport implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "airport_seq")
    @SequenceGenerator(name = "airport_seq", sequenceName = "id_airport_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_airport", nullable = false)
    private Integer idAirport;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "city_id")
    private Integer cityId;

    @ManyToOne
    @JoinColumn(name = "city_id", insertable = false, updatable = false)
    private DestinationCity city;

    public Airport(String name, String address, Integer cityId) {
        this.name = name;
        this.address = address;
        this.cityId = cityId;
    }
}
