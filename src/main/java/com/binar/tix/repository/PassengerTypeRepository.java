package com.binar.tix.repository;

import com.binar.tix.entities.PassengerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerTypeRepository extends JpaRepository<PassengerType, Integer>{
}
