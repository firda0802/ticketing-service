package com.binar.tix.payload;

import com.binar.tix.entities.Facility;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RespSchedule {
    private List<Facility> facility;
    private List<RespScheduleData> schedule;
}
