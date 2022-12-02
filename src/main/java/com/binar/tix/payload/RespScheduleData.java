package com.binar.tix.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class RespScheduleData {

    private int scheduleId;
    private String classType;
    private String luggageCapacity;
    private String departureCity;
    private String destinationsCity;
    private String airplane;
    private String airport;
    private String airportAddress;
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+7")
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+7")
    private LocalTime endTime;
    private int price;
}
