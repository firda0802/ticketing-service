package com.binar.tix.controller;


import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqCreateAirport;
import com.binar.tix.service.AirportService;
import com.binar.tix.utility.HttpUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.log.SysoLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/airport")
public class AirportController {

    @Autowired
    AirportService airportService;

    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping(value = "/add-update-airport")
    public ResponseEntity<Messages> addUpdateairport(@RequestBody ReqCreateAirport req, HttpServletRequest httpServletRequest, SysoLogger log) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(req));
        log.info(writeLog);
        airportService.updateAirport(req.getId(), req.getName(), req.getAddress());
        Messages resp = new Messages();
        resp.success();
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(resp));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @DeleteMapping(value = "/delete-airport/{airportId}")
    public ResponseEntity<Messages> deleteAirport(@PathVariable(name = "airportId") Integer airportId, HttpServletRequest httpServletRequest, SysoLogger log) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(airportId));
        log.info(writeLog);
        Messages resp = new Messages();
        Boolean status = airportService.deleteAirport(airportId);
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

