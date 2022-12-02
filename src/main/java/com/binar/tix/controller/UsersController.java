/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.controller;

import com.binar.tix.entities.Notifications;
import com.binar.tix.entities.Users;
import com.binar.tix.payload.*;
import com.binar.tix.service.NotificationService;
import com.binar.tix.utility.Constant;
import com.binar.tix.utility.HttpUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.binar.tix.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Riko
 */
@Slf4j
@RestController
public class UsersController {

    @Autowired
    UserService userService;

    @Autowired
    NotificationService notifService;

    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping(value = "/ext/register")
    public ResponseEntity<Messages> register(@RequestBody ReqSigninup req, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(req));
        log.info(writeLog);

        boolean emailValid = EmailValidator.getInstance().isValid(req.getEmail().toLowerCase());
        Messages resp = new Messages();
        if (emailValid) {
            String createUser = userService.registerUser(req);
            if (createUser.length() > 0) {
                resp.success();
                resp.setData(createUser);
            } else {
                resp.setResponseCode(Constant.ALREADY_EXIST);
                resp.setResponseMessage("Email sudah terdaftar");
            }
            String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
            log.info(writeLogResp);
            return ResponseEntity.ok().body(resp);
        } else {
            Messages msg = new Messages(Constant.BAD_REQUEST, "Email tidak valid");
            String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(msg));
            log.info(writeLogResp);
            return ResponseEntity.status(Constant.BAD_REQUEST).body(msg);
        }
    }

    @PostMapping(value = "/ext/login")
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
            resp.setResponseMessage("Email atau password salah");
        }

        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping(value = "/users/count-notif")
    public ResponseEntity<Messages> countNotif(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);

        Integer userId = (Integer) httpServletRequest.getAttribute(Constant.USER_ID);
        Messages resp = new Messages();
        resp.success();
        resp.setData(new RespNotifCount(notifService.countNotifUsers(userId)));

        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping(value = "/users/my-profile")
    public ResponseEntity<Messages> myprofile(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);

        Integer userId = (Integer) httpServletRequest.getAttribute("userId");
        Messages resp = new Messages();
        Users users = userService.getOneUsers(userId);
        RespProfil profil = new RespProfil();
        profil.setUserId(users.getUserId());
        profil.setFullName(users.getFullName());
        profil.setEmail(users.getFullName());
        profil.setAddress(users.getAddress());
        profil.setPhoneNo(users.getPhoneNo());
        profil.setBirthDate(users.getBirthDate());
        resp.setData(profil);
        resp.success();

        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @PutMapping(value = "/users/update-profile")
    public ResponseEntity<Messages> update(@RequestBody ReqUpdateUser req, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);

        Integer userId = (Integer) httpServletRequest.getAttribute(Constant.USER_ID);
        Messages resp = new Messages();
        Boolean users = userService.updateUser(userId, req);
        if (Boolean.TRUE.equals(users)) {
            resp.success();
        } else {
            resp.notFound();
        }

        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @DeleteMapping(value = "/users/delete-user")
    public ResponseEntity<Messages> deleteUser(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);

        Integer userId = (Integer) httpServletRequest.getAttribute(Constant.USER_ID);
        Messages resp = new Messages();
        userService.deleteUser(userId);
        resp.success();

        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(resp));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }


    @GetMapping(value = "/users/get-notif")
    public ResponseEntity<Messages> getNotif(@RequestParam(name = "limit") int limit, @RequestParam(name = "pageNumber") int pageNumber, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("limit : " + limit + " , pageNumber : " + pageNumber));
        log.info(writeLog);

        Integer userId = (Integer) httpServletRequest.getAttribute(Constant.USER_ID);
        if (limit < 1 || pageNumber < 1) {
            return ResponseEntity.status(Constant.BAD_REQUEST).body(new Messages(Constant.BAD_REQUEST, "limit dan pageNumber invalid"));
        } else {
            Messages resp = new Messages();
            Pageable paging = PageRequest.of(pageNumber - 1, limit, Sort.by("cdate").descending());
            Page<Notifications> data = notifService.getNotifUsers(paging, userId);
            if (!data.isEmpty()) {
                PagingResponse dataPaging = new PagingResponse();
                dataPaging.setTotalData(data.getTotalElements());
                dataPaging.setTotalPaging(data.getTotalPages());
                resp.setPaging(dataPaging);
                resp.setData(data.getContent());
                resp.success();
            } else {
                resp.notFound();
            }
            String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
            log.info(writeLogResp);
            return ResponseEntity.ok().body(resp);
        }
    }
}
