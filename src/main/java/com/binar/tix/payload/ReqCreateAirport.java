package com.binar.tix.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCreateAirport {
    private int id;
    private String name;
    private String address;
}
