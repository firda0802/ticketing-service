package com.binar.tix.service;

import com.binar.tix.entities.Schedule;
import com.binar.tix.payload.ReqCreateSchedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleService {


    List<Schedule> findAllSchedule();

    Optional<Schedule> findByScheduleId(Integer scheduleId);

    Schedule saveSchedule(ReqCreateSchedule schedule);

    Boolean updateSchedule(Integer scheduleId, ReqCreateSchedule schedule);

    Boolean deleteSchedule(Integer scheduleId);
}
