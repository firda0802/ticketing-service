/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.service;

import com.binar.tix.payload.ReqSigninup;
import java.util.Optional;

import org.bouncycastle.ocsp.Req;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author Riko
 */
public interface UserService {

    Boolean registerUser(ReqSigninup req);
    Optional<User> validateSession(String session);
    String login(ReqSigninup req);
}
