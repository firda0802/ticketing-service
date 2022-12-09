package com.binar.tix.payload;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RespHistoryDetailtem {

    private String name;
    private String type;
    private String seatsNumber;
    private int luggageCapacity;
    private TravelDocument travelDocument;

}
