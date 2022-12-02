package com.binar.tix.repository;

import com.binar.tix.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    List<Schedule> findByDestinationIdAndClassIdAndFlightDate(int destinationId, int classId, LocalDate flightDate);
}