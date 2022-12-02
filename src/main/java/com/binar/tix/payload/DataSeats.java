package com.binar.tix.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSeats {
    private String seatsNumber;
    private String seatsGroup;
    private int classSeats;
    private Integer position;
    private String status;
}
