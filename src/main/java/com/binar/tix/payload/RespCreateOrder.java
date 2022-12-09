package com.binar.tix.payload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter @RequiredArgsConstructor
public class RespCreateOrder {

    private String destination;
    private String status;

    public RespCreateOrder(String destination, String status) {
        this.destination = destination;
        this.status = status;
    }
}
