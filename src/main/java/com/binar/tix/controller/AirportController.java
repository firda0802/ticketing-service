package com.binar.tix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqCreateAirport;
import com.binar.tix.service.CrudService;
import com.binar.tix.service.InitializeService;
import com.binar.tix.utility.HttpUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/airport")
@Slf4j
public class AirportController {

    @Autowired
    CrudService airportService;

    @Autowired
    InitializeService initializeService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Airport List", description = "Menampilkan seluruh penerbangan yang tersedia didalam aplikasi.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "    \"data\": \"idAirport\": 18, \n" +
                            "name\": \"Bandar Udara Internasional Soekarno–Hatta\",\n" +
                            "address\": \"Kota Tangerang, Banten 19120\",\n" +
                            "cityId\": 1,\n" +
                            "city\": \"destinationCityId\": 1," + "cityName\": \"Jakarta\", \n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @GetMapping(value = "/get-list")
    public ResponseEntity<Messages> listAirport(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);
        Messages resp = new Messages();
        resp.success();
        resp.setData(initializeService.getAirport());
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(resp));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Add Update airport", description = "Dapat Melakukan update pada penerbangan di aplikasi.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @PostMapping(value = "/add-update-airport")
    public ResponseEntity<Messages> addUpdateairport(@RequestBody ReqCreateAirport req,
            HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(req));
        log.info(writeLog);
        airportService.updateAirport(req.getId(), req.getName(), req.getAddress(), req.getCityId());
        Messages resp = new Messages();
        resp.success();
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(resp));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Delete airport", description = "Dapat menghapus airport jika id tersedia.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @DeleteMapping(value = "/delete/{airportId}")
    public ResponseEntity<Messages> deleteAirport(@PathVariable(name = "airportId") Integer airportId,
            HttpServletRequest httpServletRequest) throws JsonProcessingException {
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
