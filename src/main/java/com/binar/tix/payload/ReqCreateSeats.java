package com.binar.tix.payload;

import com.binar.tix.entities.ClassSeats;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCreateSeats {

    private String seatsNumber;

    private char seatsGroup;

    private Integer positions;

    private Integer classId;

    private Integer airplanesId;

    private ClassSeats classSeats;

    public Integer getSeatsId(int i) {
        return null;
    }

    public void setClassSeats(String string) {
    }

    public void setSeatsGroup(String string) {
    }

}
