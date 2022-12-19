package com.binar.tix.service;

import com.binar.tix.entities.Schedule;
import com.binar.tix.payload.ReqCreateSchedule;
import com.binar.tix.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    @Autowired
    ScheduleRepository scheduleRepository;

    @Override
    public List<Schedule> findAllSchedule() {
        return scheduleRepository.findAll();
    }

    @Override
    public Optional<Schedule> findByScheduleId(Integer scheduleId){
        return scheduleRepository.findById(scheduleId);
    }

    @Override
    public Schedule saveSchedule(ReqCreateSchedule schedule) {
        Schedule schedule1 = new Schedule();
        schedule1.setDestinationId(schedule.getDestinationId());
        schedule1.setClassId(schedule.getClassId());
        schedule1.setPrice(schedule.getPrice());
        schedule1.setAirplanesId(schedule.getAirplanesId());
        schedule1.setFlight(schedule.getFlight());
        return scheduleRepository.save(schedule1);
    }

    @Override
    public Boolean updateSchedule(Integer scheduleId,ReqCreateSchedule schedule) {
        Optional<Schedule> schedules = scheduleRepository.findById(scheduleId);
        if (schedules.isPresent()) {
            Schedule schedule1 = schedules.get();
            schedule1.setDestinationId(schedule.getDestinationId());
            schedule1.setClassId(schedule.getClassId());
            schedule1.setPrice(schedule.getPrice());
            schedule1.setAirplanesId(schedule.getAirplanesId());
            schedule1.setFlight(schedule.getFlight());
            scheduleRepository.saveAndFlush(schedule1);
            return true;
        } else {
            return false;
        }
    }

    public Boolean deleteSchedule(Integer scheduleId) {
        Optional<Schedule> schedules = scheduleRepository.findById(scheduleId);
        if (schedules.isPresent()) {
            Schedule schedule1= schedules.get();
            scheduleRepository.delete(schedule1);
            return true;
        } else {
            return false;
        }
    }
}
