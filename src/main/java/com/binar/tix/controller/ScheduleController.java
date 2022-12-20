package com.binar.tix.controller;

import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqCreateSchedule;
import com.binar.tix.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


@Slf4j
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Operation(summary="Menambahkan Jadwal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Jadwal berhasil ditambahkan.",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))}),
            @ApiResponse(responseCode = "400", description = "Eror dari sisi Browser .",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))}),
            @ApiResponse(responseCode = "500", description = "Eror dari sisi Server.",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))})})
    @PostMapping("/add-schedule")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Messages> create(@RequestBody ReqCreateSchedule schedule){
        scheduleService.saveSchedule(schedule);
        Messages messages= new Messages();
        messages.setResponseCode(201);
        messages.setResponseMessage("Berhasil Ditambahkan");
        return ResponseEntity.ok().body(messages);
    }

    @Operation(summary="Menampilkan Jadwal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "", description = ".",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))}),
            @ApiResponse(responseCode = "400", description = "Eror dari sisi Browser .",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))}),
            @ApiResponse(responseCode = "500", description = "Eror dari sisi Server.",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))})})
    @GetMapping("/get-schedule")
    public ResponseEntity<Messages> get(){

        Messages messages= new Messages();
        messages.setResponseCode(200);
        messages.setResponseMessage("Berhasil Ditampilkan");
        messages.setData(scheduleService.findAllSchedule());
        return ResponseEntity.ok().body(messages);
    }

    @Operation(summary="Mengupdate Data Jadwal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "", description = ".",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))}),
            @ApiResponse(responseCode = "400", description = "Eror dari sisi Browser .",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))}),
            @ApiResponse(responseCode = "500", description = "Eror dari sisi Server.",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))})})
    @PutMapping("/update-schedule/{scheduleId}")
    public ResponseEntity<Messages> update(@PathVariable Integer scheduleId, @RequestBody ReqCreateSchedule schedule){
        Messages messages= new Messages();
        Boolean status = scheduleService.updateSchedule(scheduleId,schedule);
        if (Boolean.TRUE.equals(status)) {
            scheduleService.updateSchedule(scheduleId,schedule);
            messages.setResponseCode(200);
            messages.setResponseMessage("Berhasil Diupdate");
        }
        else {
            messages.setResponseCode(204);
            messages.setResponseMessage("Data tidak ditemukan");
        }
        return ResponseEntity.ok().body(messages);
    }

    @Operation(summary="Menghapus Jadwal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "", description = ".",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))}),
            @ApiResponse(responseCode = "400", description = "Eror dari sisi Browser .",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))}),
            @ApiResponse(responseCode = "500", description = "Eror dari sisi Server.",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))})})
    @DeleteMapping("/delete-schedule/{scheduleId}")
    public ResponseEntity<Messages> delete(@PathVariable Integer scheduleId){
        Messages messages= new Messages();
        Boolean status = scheduleService.deleteSchedule(scheduleId);
        if (Boolean.TRUE.equals(status)) {
            scheduleService.deleteSchedule(scheduleId);
            messages.setResponseCode(200);
            messages.setResponseMessage("Berhasil Dihapus");
        }
        else {
            messages.setResponseCode(204);
            messages.setResponseMessage("Data tidak ditemukan");
        }
        return ResponseEntity.ok().body(messages);
    }
}
