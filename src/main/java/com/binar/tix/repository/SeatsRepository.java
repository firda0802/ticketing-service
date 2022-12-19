package com.binar.tix.repository;

import com.binar.tix.entities.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatsRepository extends JpaRepository<Seats, Integer> {

    Seats findByClassIdAndAirplanesIdAndSeatsNumber(int classId, int airplaneId, String seatsNumber);

    Optional<Seats> findById(Integer seatsId);

}
