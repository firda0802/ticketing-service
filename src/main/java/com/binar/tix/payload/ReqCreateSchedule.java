package com.binar.tix.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCreateSchedule {

    private Integer destinationId;
    private Integer classId;
    private Integer price;
    private Integer airplanesId;
    private String flight;
}
