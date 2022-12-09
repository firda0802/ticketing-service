package com.binar.tix.payload;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RespScheduleReturn {
    private int scheduleId;
    private int airplaneId;
    private int classId;
}
