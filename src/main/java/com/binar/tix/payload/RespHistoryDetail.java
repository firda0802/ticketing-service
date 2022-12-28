package com.binar.tix.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter @Setter
public class RespHistoryDetail {

    private String invoiceNo;
    private String qrCodeUrl;
    private String airplane;
    private String classType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private LocalDateTime paymentDate;
    private String bookingBy;
    @JsonFormat(pattern = "EEEE, dd MMMM yyyy", timezone = "GMT+7")
    private LocalDate flightDate;
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+7")
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+7")
    private LocalTime endTime;
    private String departureCity;
    private String departureAirport;
    private String destinationsCity;
    private String destinationsAirport;
    private int totalPerson;
    private List<RespHistoryDetailtem> detail;
    private String paymentName;
    private Integer amount;

}
