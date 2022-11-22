/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.controller;

import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqSigninup;
import com.binar.tix.payload.RespLogin;
import com.binar.tix.utility.Constant;
import com.binar.tix.utility.HttpUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.binar.tix.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Riko
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService userService;

    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping(value = "/register")
    public ResponseEntity<Messages> register(@RequestBody ReqSigninup req, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(req));
        log.info(writeLog);

        boolean emailValid = EmailValidator.getInstance().isValid(req.getEmail().toLowerCase());
        Messages resp = new Messages();
        if(emailValid){
            boolean createUser = userService.registerUser(req);
            if(createUser){
                resp.success();
            }else{
                resp.setResponseCode(Constant.ALREADY_EXIST);
                resp.setResponseMessage("Email sudah terdaftar");
            }
            String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(resp));
            log.info(writeLogResp);
            return ResponseEntity.ok().body(resp);
        }else{
            Messages msg = new Messages(Constant.BAD_REQUEST, "Email tidak valid");
            String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(msg));
            log.info(writeLogResp);
            return ResponseEntity.status(Constant.BAD_REQUEST).body(msg);
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Messages> login(@RequestBody ReqSigninup req, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(req));
        log.info(writeLog);

        String token = userService.login(req);
        Messages resp = new Messages();
        if (token.length() > 0) {
            resp.success();
            RespLogin respLogin = new RespLogin();
            respLogin.setToken(token);
            resp.setData(respLogin);
        } else {
            resp.setResponseCode(Constant.NO_CONTENT);
            resp.setResponseMessage("Email atau password tidak valid");
        }

        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }
}
