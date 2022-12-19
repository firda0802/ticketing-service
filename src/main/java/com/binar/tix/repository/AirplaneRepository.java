package com.binar.tix.repository;

import com.binar.tix.entities.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirplaneRepository  extends JpaRepository<Airplane, Integer>{
    Optional<Airplane> findById(Integer airplaneId);

}
