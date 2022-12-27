package com.binar.tix.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqUpdatePayment;
import com.binar.tix.service.AdminService;
import com.binar.tix.service.InitializeService;
import com.binar.tix.utility.HttpUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    InitializeService initService;

    @Autowired
    AdminService adminService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Payment List", description = "Admin dapat menampilkan list pembayaran yang telah di lakukan user pada aplikasi.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "    \"data\": \"paymentId\": 30, \n" +
                            "paymentMethod\": \"DANA\",\n" +
                            "status\": true\"\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @GetMapping(value = "/list-payment")
    public ResponseEntity<Messages> listPayment(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);

        Messages resp = new Messages();
        resp.success();
        resp.setData(initService.listPayment());
        String writeLogResp = HttpUtility.writeLogResp(
                mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Create Payment", description = "Admin dapat membuat pembayaran.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @PostMapping(value = "/create-payment")
    public ResponseEntity<Messages> createPayment(@RequestBody ReqUpdatePayment req,
            HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);

        Messages resp = new Messages();
        adminService.createPayment(req);
        resp.success();
        String writeLogResp = HttpUtility.writeLogResp(
                mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Update Payment", description = "Admin dapat melakukan update pada pembayaran.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @PutMapping(value = "/update-payment/{idPayment}")
    public ResponseEntity<Messages> updatePayment(@PathVariable(name = "idPayment") int idPayment,
            @RequestBody ReqUpdatePayment req, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);

        Messages resp = new Messages();
        boolean update = adminService.updatePayment(idPayment, req);
        if (update) {
            resp.success();
        } else {
            resp.notFound();
        }
        String writeLogResp = HttpUtility.writeLogResp(
                mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Delete Payment", description = "Admin dapat melakukan menonaktifkan pembayaran ataupun menghapus pembayaran yang tersedia.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @DeleteMapping(value = "/disable-payment/{idPayment}")
    public ResponseEntity<Messages> disablePayment(@PathVariable(name = "idPayment") int idPayment,
            HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);

        Messages resp = new Messages();
        adminService.disablePayment(idPayment);
        resp.success();
        String writeLogResp = HttpUtility.writeLogResp(
                mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    public static org.slf4j.Logger getLog() {
        return log;
    }

    public InitializeService getInitService() {
        return initService;
    }

    public void setInitService(InitializeService initService) {
        this.initService = initService;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

}
