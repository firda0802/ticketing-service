package com.binar.tix.repository;

import com.binar.tix.entities.Notiffication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notiffication,Integer> {
}
