package com.binar.tix.controller;

import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqCreateSchedule;
import com.binar.tix.service.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    CrudService scheduleService;

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Menambahkan Jadwal",
                            description = "Penambahan jadwal selain dari jadwal yang sudah tersedia.",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Berhasil Ditambahkan\",\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @PostMapping("/add-schedule")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Messages> create(@RequestBody ReqCreateSchedule schedule){
        scheduleService.saveSchedule(schedule);
        Messages messages= new Messages();
        messages.setResponseCode(201);
        messages.setResponseMessage("Berhasil Ditambahkan");
        return ResponseEntity.ok().body(messages);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Menampilkan Jadwal",
                            description = "Menampilkan semua jadwal yang tersedia",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Berhasil Ditampilkan\",\n" +
                                    "    \"data\": \"eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQ1LCJlbWFpbCI6InJpa29qYW51YXJzYXdhbHVkc3NpbnNzQGdtYWlsLmNvbSIsInJvbGUiOiJCVVlFUiIsImp0aSI6ImFkOWM4MzllLWJiMWEtNDBlMS1iZGM3LWY2NDk3MzMwMDM5YSIsImlhdCI6MTY3MTQzMzQ4OCwiZXhwIjoxNjc0MDI1NDg4fQ.rC9MG_QV1zhR9f78GYL_JCsNGq1a3iGYu9SDCOE7jck\"\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @GetMapping("/get-schedule")
    public ResponseEntity<Messages> get(){

        Messages messages= new Messages();
        messages.setResponseCode(200);
        messages.setResponseMessage("Berhasil Ditampilkan");
        messages.setData(scheduleService.findAllSchedule());
        return ResponseEntity.ok().body(messages);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Mengupdate Jadwal",
                            description = "Update jadwal untuk perubahan pada jadwal tertentu",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Berhasil Diupdate\",\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
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

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Menghapus Jadwal",
                            description = "Hapus jadwal untuk menghapus jadwal yang tidak tersedia berdasarkan id nya",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Berhasil Dihapus\",\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
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
