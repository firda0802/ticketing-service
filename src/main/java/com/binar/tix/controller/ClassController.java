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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

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

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Class List", description = "Menampilkan list kelas beserta nama dan harga untuk para penumpang berdasarkan id.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "    \"data\": \"classId\": 1, \n" +
                            "name\": Economi Class\"\n" +
                            "price\": 35.000\"\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
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

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Add Update Class", description = "Dapat Melakukan update pada kelas di aplikasi.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @PostMapping(value = "/add-update-class")
    public ResponseEntity<Messages> addUpdateClass(@RequestBody ReqUpdateClass req,
            HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(req));
        log.info(writeLog);
        boolean status = classService.updateAddClass(req);
        Messages resp = new Messages();
        if (status) {
            resp.success();
        } else {
            resp.notFound();
        }

        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(resp));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Delete Class", description = "Dapat menghapus kelas jika id tersedia.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @DeleteMapping(value = "/delete/{classId}")
    public ResponseEntity<Messages> deleteClass(@PathVariable(name = "classId") Integer classId,
            HttpServletRequest httpServletRequest) throws JsonProcessingException {
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
