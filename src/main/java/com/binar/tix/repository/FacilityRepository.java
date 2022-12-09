package com.binar.tix.repository;

import com.binar.tix.entities.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, Integer> {

    List<Facility> findByClassId(int classId);
}