package com.binar.tix.repository;

import com.binar.tix.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository <Airport, Integer> {

}
