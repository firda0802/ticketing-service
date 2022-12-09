package com.binar.tix.payload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ScheduleAirplane {

    private int airplanesId;
    private int price;
    private String flight;

    public ScheduleAirplane(int airplanesId, int price, String flight) {
        this.airplanesId = airplanesId;
        this.price = price;
        this.flight = flight;
    }
}
