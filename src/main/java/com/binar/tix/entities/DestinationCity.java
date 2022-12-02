package com.binar.tix.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "destination_city")
public class DestinationCity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "destination_city_id")
    private Integer destinationCityId;

    @Column(name = "city_name")
    private String cityName;

    public DestinationCity() {
    }

    public DestinationCity(String cityName) {
        this.cityName = cityName;
    }
}
