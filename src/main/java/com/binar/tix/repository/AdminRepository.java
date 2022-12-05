package com.binar.tix.repository;

import com.binar.tix.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByAdminIdAndStatus(int admin_Id, Boolean status);

    Optional<Admin> findByPasswordAndStatus(String password, Boolean status);
}
