package com.binar.tix.repository;

import com.binar.tix.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AirportRepository extends JpaRepository <Airport, Integer> {

    @Query(nativeQuery = true, value = "select * from airport where city_id = :cityId limit 1")
    Airport getAirport(@Param("cityId") int cityId);

}
