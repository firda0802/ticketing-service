package com.binar.tix.controller;

import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqCreateAirport;
import com.binar.tix.payload.ReqUpdateClass;
import com.binar.tix.service.CrudService;
import com.binar.tix.service.InitializeService;
import com.binar.tix.utility.HttpUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/class")
@Slf4j
public class ClassController {

    @Autowired
    InitializeService initializeService;

    @Autowired
    CrudService classService;
    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping(value = "/get-list")
    public ResponseEntity<Messages> listClass(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);
        Messages resp = new Messages();
        resp.success();
        resp.setData(initializeService.listClass());
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(resp));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @PostMapping(value = "/add-update-class")
    public ResponseEntity<Messages> addUpdateClass(@RequestBody ReqUpdateClass req, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(req));
        log.info(writeLog);
        boolean status = classService.updateAddClass(req);
        Messages resp = new Messages();
        if(status){
            resp.success();
        }else{
            resp.notFound();
        }

        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(resp));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @DeleteMapping(value = "/delete/{classId}")
    public ResponseEntity<Messages> deleteClass(@PathVariable(name = "classId") Integer classId, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(classId));
        log.info(writeLog);
        Messages resp = new Messages();
        Boolean status = classService.deleteClassSeats(classId);
        if (Boolean.TRUE.equals(status)) {
            resp.success();
        } else {
            resp.notFound();
        }
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(resp));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

}
