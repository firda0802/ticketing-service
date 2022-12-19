package com.binar.tix.repository;

import com.binar.tix.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    List<Schedule> findByDestinationIdAndClassIdAndFlightDate(int destinationId, int classId, LocalDate flightDate);

    Schedule findByDestinationIdAndClassIdAndFlightDateAndStartTimeAndEndTime(int destinationId, int classId, LocalDate flightDate, LocalTime startTime, LocalTime endTime);

    Optional<Schedule> findById(Integer scheduleId);
}