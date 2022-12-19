package com.binar.tix.payload;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCreatePayment {

    private String paymentMethod;
    private Boolean status;
}
