/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.repository;

import com.binar.tix.entities.Users;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 *
 * @author Riko
 */
public interface UsersRepository extends JpaRepository<Users, Integer> {

    List<Users> findByRoleId(int roleId);

    Optional<Users> findByUserIdAndStatus(int userId, Boolean status);
    Optional<Users> findByEmailIgnoreCaseAndPasswordAndStatus(String email, String password, Boolean status);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Users a set a.status = false where a.userId =:userId")
    void deleteAkun(@Param("userId") int userId);
    Optional<Users> findByEmailIgnoreCaseAndStatusAndRoleId(String email, Boolean status, int roleId);

    Optional<Users> findByEmailIgnoreCaseAndStatus(String email, Boolean status);
}
