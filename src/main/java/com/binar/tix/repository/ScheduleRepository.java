package com.binar.tix.repository;

import com.binar.tix.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    List<Schedule> findByDestinationIdAndClassIdAndFlightDate(int destinationId, int classId, LocalDate flightDate);

    Schedule findByDestinationIdAndClassIdAndFlightDateAndStartTimeAndEndTime(int destinationId, int classId, LocalDate flightDate, LocalTime startTime, LocalTime endTime);

    Optional<Schedule> findById(Integer scheduleId);

    @Query(nativeQuery = true, value = "select * from schedule limit :limit offset :offset ")
    List<Schedule> getSchedule(@Param("limit") int limit, @Param("offset") int offset);
}