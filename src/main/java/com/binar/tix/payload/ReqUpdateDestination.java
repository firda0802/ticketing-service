package com.binar.tix.payload;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReqUpdateDestination {
    private int id;
    private int destinationCityId;
    private int departureCityId;
    private int duration;
    private int price;
}
