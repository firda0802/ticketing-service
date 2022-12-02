/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.service;

import com.binar.tix.entities.RoleUser;
import com.binar.tix.entities.Users;
import com.binar.tix.payload.ReqSigninup;

import java.util.List;
import java.util.Optional;

import com.binar.tix.payload.ReqUpdateUser;
import org.springframework.security.core.userdetails.User;


/**
 *
 * @author Riko
 */
public interface UserService {

    Users getOneUsers(int userId);

    void addRole(RoleUser name);

    List<RoleUser> getAllRole();
    void deleteUser(int userId);
    Boolean updateUser(int userId, ReqUpdateUser req);
    String registerUser(ReqSigninup req);
    Optional<User> validateSession(String session);
    String login(ReqSigninup req);
}
