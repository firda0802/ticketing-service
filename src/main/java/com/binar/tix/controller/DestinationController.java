package com.binar.tix.controller;

import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqUpdateClass;
import com.binar.tix.payload.ReqUpdateDestination;
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
@RequestMapping("/dest")
@Slf4j
public class DestinationController {

    @Autowired
    InitializeService initializeService;

    @Autowired
    CrudService destinationService;

    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping(value = "/get-list")
    public ResponseEntity<Messages> listDestination(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);
        Messages resp = new Messages();
        resp.success();
        resp.setData(initializeService.getDestination());
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(resp));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @PostMapping(value = "/add-update")
    public ResponseEntity<Messages> addUpdateDestination(@RequestBody ReqUpdateDestination req, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(req));
        log.info(writeLog);
        boolean status = destinationService.updateAddDestination(req);
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

    @DeleteMapping(value = "/delete/{destId}")
    public ResponseEntity<Messages> deleteClass(@PathVariable(name = "destId") Integer destId, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(destId));
        log.info(writeLog);
        Messages resp = new Messages();
        Boolean status = destinationService.deleteDestination(destId);
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
