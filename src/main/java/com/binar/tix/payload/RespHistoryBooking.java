package com.binar.tix.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class RespHistoryBooking {

    private String invoiceNo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    private LocalDateTime paymentDate;
    private String bookingBy;
    private int totalPerson;
    private String destination;
    private String classType;
    private String paymentName;
    private Integer amount;


}
