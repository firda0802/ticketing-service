/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.repository;

import com.binar.tix.entities.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Riko
 */
public interface RoleUserRepository extends JpaRepository<RoleUser, Integer>{
    
}
