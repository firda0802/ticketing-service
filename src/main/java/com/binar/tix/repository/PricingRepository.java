package com.binar.tix.repository;

import com.binar.tix.entities.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingRepository extends JpaRepository<Pricing, Integer> {
}