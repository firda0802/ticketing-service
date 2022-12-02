package com.binar.tix.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Getter
@Setter
@Table(name = "destination")
public class Destination implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "destination_id")
    private Integer destinationId;

    @Column(name = "departure")
    private int departure;

    @ManyToOne
    @JoinColumn(name = "departure", insertable = false, updatable = false)
    private DestinationCity departureCity;

    @Column(name = "destinations")
    private int destinations;

    @ManyToOne
    @JoinColumn(name = "destinations", insertable = false, updatable = false)
    private DestinationCity destinationsCity;

    @Column(name = "additional_price")
    private int additionalPrice;

    @Column(name = "duration")
    private Integer duration;
    public Destination() {}

    public Destination(int departure, int destinations, int additionalPrice, Integer duration) {
        this.departure = departure;
        this.destinations = destinations;
        this.additionalPrice = additionalPrice;
        this.duration = duration;
    }
}
