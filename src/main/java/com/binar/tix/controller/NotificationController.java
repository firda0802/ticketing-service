/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.controller;

import com.binar.tix.entities.NotifCategory;
import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqNotifCategory;
import com.binar.tix.service.NotificationService;
import com.binar.tix.utility.HttpUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Riko
 */
@Slf4j
@RestController
@RequestMapping("/notif")
public class NotificationController {

    @Autowired
    NotificationService notifService;

    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping(value = "/add-update-category")
    public ResponseEntity<Messages> addUpdateNotifCategory(@RequestBody ReqNotifCategory req, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(req));
        log.info(writeLog);
        notifService.createUpdateNotifCategory(req.getId(), req.getName());
        Messages resp = new Messages();
        resp.success();
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(resp));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @DeleteMapping(value = "/delete-category/{categoryId}")
    public ResponseEntity<Messages> deleteCategory(@PathVariable(name = "categoryId") Integer categoryId, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(categoryId));
        log.info(writeLog);
        Messages resp = new Messages();
        Boolean status = notifService.deleteCategory(categoryId);
        if (Boolean.TRUE.equals(status)) {
            resp.success();
        } else {
            resp.notFound();
        }
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(resp));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping(value = "/get-notifcategory")
    public ResponseEntity<Messages> getNotifCategory(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);
        Messages resp = new Messages();
        List<NotifCategory> data = notifService.getAllNotifCategory();
        if (!data.isEmpty()) {
            resp.success();
            resp.setData(data);
        } else {
            resp.notFound();
        }
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(resp));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

}
