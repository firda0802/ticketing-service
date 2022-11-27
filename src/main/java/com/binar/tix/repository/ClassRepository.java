package com.binar.tix.repository;

import com.binar.tix.entities.ClassSeats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<ClassSeats, Integer> {
}
