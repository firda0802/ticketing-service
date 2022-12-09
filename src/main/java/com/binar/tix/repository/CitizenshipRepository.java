package com.binar.tix.repository;

import com.binar.tix.entities.Citizenship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitizenshipRepository extends JpaRepository<Citizenship, Integer> {
}