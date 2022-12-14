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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Add-Update Kategori Notifikasi",
                            description = "Untuk menambahkan dan mengubah suatu kategori notifikasi",
                            value = "{\n" +
                                    "  \"responseCode\": 200,\n" +
                                    "  \"responseMessage\": \"Sukses\",\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
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

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Menghapus Kategori Notifikasi",
                            description = "Hapus kategori untuk menghapus kategori notifikasi berdasarkan id kategorinya.",
                            value = "{\n" +
                                    "  \"responseCode\": 200,\n" +
                                    "  \"responseMessage\": \"Sukses\",\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
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

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Menampilkan Kategori Notifikasi",
                            description = "Tampil kategori untuk menampilkan kategori dari notifikasi.",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Sukses\",\n" +
                                    "    \"data\": [\n" +
                                    "        {\n" +
                                    "            \"notificationCategoryId\": 1,\n" +
                                    "            \"notifcategoryName\": \"Introduce\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"notificationCategoryId\": 2,\n" +
                                    "            \"notifcategoryName\": \"Booking\"\n" +
                                    "        }" +
                                    "    ]\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
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
