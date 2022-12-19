package com.binar.tix.payload;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReqCreateAirplane {

    private String type;
    private Integer luggageCapacity;
    private int airportId;
}
