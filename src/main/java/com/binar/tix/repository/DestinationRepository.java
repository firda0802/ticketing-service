package com.binar.tix.repository;

import com.binar.tix.entities.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Integer> {
    Optional<Destination> findByDepartureAndDestinations(int departure, int destinations);
}
