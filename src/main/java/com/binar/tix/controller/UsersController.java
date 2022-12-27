/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.controller;

import com.binar.tix.entities.Notifications;
import com.binar.tix.entities.Users;
import com.binar.tix.google.SigninWithGoogle;
import com.binar.tix.payload.*;
import com.binar.tix.service.NotificationService;
import com.binar.tix.service.UserService;
import com.binar.tix.utility.Constant;
import com.binar.tix.utility.HttpUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;

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

    @Autowired
    SigninWithGoogle googleOauth;

    private final ObjectMapper mapper = new ObjectMapper();

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Registrasi User",
                            description = "User harus melakukan registrasi untuk mendapatkan akun yang bisa digunakan login pada aplikasi. Email hanya bisa didaftarkan 1x saja dan alamat email harus valid. Jika registrasi berhasil, akan mendapatkan return token yang digunakan untuk mengakses api lainnya yang memerlukan header Authorization sesuai dengan role-nya.",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Sukses\",\n" +
                                    "    \"data\": \"eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQ1LCJlbWFpbCI6InJpa29qYW51YXJzYXdhbHVkc3NpbnNzQGdtYWlsLmNvbSIsInJvbGUiOiJCVVlFUiIsImp0aSI6ImFkOWM4MzllLWJiMWEtNDBlMS1iZGM3LWY2NDk3MzMwMDM5YSIsImlhdCI6MTY3MTQzMzQ4OCwiZXhwIjoxNjc0MDI1NDg4fQ.rC9MG_QV1zhR9f78GYL_JCsNGq1a3iGYu9SDCOE7jck\"\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @PostMapping(value = "/ext/register")
    public ResponseEntity<Messages> register(@RequestBody ReqRegister req, HttpServletRequest httpServletRequest) throws JsonProcessingException {
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

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Registrasi User",
                            description = "Proses login hanya valid ketika data user sesuai dengan data di database. Dengan email dan password yang valid. Ketika login berhasil, maka akan mendapatkan token yang digunakan untuk mengakses api lainnya yang memerlukan header Authorization sesuai dengan rolenya",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Sukses\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjM4LCJlbWFpbCI6ImhlbGxvQGdtYWlsLmNvbSIsInJvbGUiOiJCVVlFUiIsImp0aSI6IjYyMGVkMWQ5LTA0MzAtNDA1Ni1hM2ZhLTBjOGFjMjczYjcyNyIsImlhdCI6MTY3MTQzMzQ0NiwiZXhwIjoxNjc0MDI1NDQ2fQ.uA1XLoZMEr2N7TF3YZjwu48sdOiSJQssCHIhRBRl4hU\",\n" +
                                    "        \"role\": \"BUYER\"\n" +
                                    "    }\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @PostMapping(value = "/ext/login")
    public ResponseEntity<Messages> login(@RequestBody ReqLogin req, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(req));
        log.info(writeLog);

        RespLogin token = userService.login(req);
        Messages resp = new Messages();
        if (token != null) {
            resp.success();
            resp.setData(token);
        } else {
            resp.setResponseCode(Constant.NO_CONTENT);
            resp.setResponseMessage("Email atau password salah");
        }

        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Menampilkan Jumlah Notifikasi",
                            description = "Menampilkan jumlah notifikasi akun yang belum dibaca oleh user.",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Sukses\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"jumlahNotif\": 1\n" +
                                    "    }\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
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

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Menampilkan User Profile",
                            description = "User profile berisi data-data user yang akan ditampilkan",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Sukses\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"userId\": 2,\n" +
                                    "        \"fullName\": \"Rizki Chrismasyadi Sianipar\",\n" +
                                    "        \"email\": \"rizki@gmail.com\",\n" +
                                    "        \"birthDate\": null,\n" +
                                    "        \"phoneNo\": \"089628224306\",\n" +
                                    "        \"address\": \"Medan\"\n" +
                                    "    }\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
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
        profil.setEmail(users.getEmail());
        profil.setAddress(users.getAddress());
        profil.setPhoneNo(users.getPhoneNo());
        profil.setBirthDate(users.getBirthDate());
        resp.setData(profil);
        resp.success();

        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Mengupdate User Profile",
                            description = "Update user profile untuk melakukan perubahan pada data user",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Sukses\"\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
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

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Menghapus Data User",
                            description = "Hapus data user untuk menghapus data dari user tertentu",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Sukses\"\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
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

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Menampilkan Notifikasi",
                            description = "Tampilkan notifikasi yang akan dikirimkan kepada user tertentu",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Sukses\",\n" +
                                    "    \"data\": [\n" +
                                    "        {\n" +
                                    "            \"notificationId\": 63,\n" +
                                    "            \"notificationCategoryId\": 1,\n" +
                                    "            \"notifcategory\": {\n" +
                                    "                \"notificationCategoryId\": 1,\n" +
                                    "                \"notifcategoryName\": \"Introduce\"\n" +
                                    "            },\n" +
                                    "            \"userId\": 53,\n" +
                                    "            \"title\": \"Hai! Selamat datang di Safety Fly\",\n" +
                                    "            \"content\": \"Nikmati layanan pemesanan tiket pesawat secara online disini\",\n" +
                                    "            \"cdate\": \"2022-12-26 14:54:20\",\n" +
                                    "            \"status\": true\n" +
                                    "        }\n" +
                                    "    ],\n" +
                                    "    \"paging\": {\n" +
                                    "        \"totalData\": 1,\n" +
                                    "        \"totalPaging\": 1\n" +
                                    "    }\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
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
                notifService.clearNotif(userId);
            } else {
                resp.notFound();
            }
            String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
            log.info(writeLogResp);
            return ResponseEntity.ok().body(resp);
        }
    }

    @PostMapping(value = "/ext/googleid-token")
    public ResponseEntity<Messages> verifyGoogleId(@RequestBody RespLogin req, HttpServletRequest httpServletRequest) throws IOException, GeneralSecurityException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(req));
        log.info(writeLog);

        Messages resp = googleOauth.verify(req.getToken());

        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

}
