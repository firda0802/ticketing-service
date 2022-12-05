package com.binar.tix.repository;

import com.binar.tix.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirportRepository extends JpaRepository <Airport, Integer> {

    static Optional<Airport> findByAirportId(Integer id) {
        return null;
    }

    static int deleteAirport(Integer id) {
        return 0;
    }
}
