package com.binar.tix.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RespSeats {
    private String groupSeats;
    private List<RespSeatsDetail> seatsList;
}
