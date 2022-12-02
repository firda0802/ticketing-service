package com.binar.tix.payload;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RespSeatsDetail {
    private String seatsNumber;
    private String statusSeats;
    private Boolean canBook;
}
