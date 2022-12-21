package com.binar.tix.controller;

import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqCreateAirplane;
import com.binar.tix.service.CrudService;
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

@Slf4j
@RestController
@RequestMapping("/airplane")
public class AirplaneController {

    @Autowired
    CrudService crudService;

    @Operation(summary="Menambahkan Pesawat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pesawat berhasil ditambahkan.",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))}),
            @ApiResponse(responseCode = "400", description = "Eror dari sisi Browser .",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))}),
            @ApiResponse(responseCode = "500", description = "Eror dari sisi Server.",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))})})
    @PostMapping("/add-airplane")
    public ResponseEntity<Messages> create(@RequestBody ReqCreateAirplane airplane){
        crudService.saveAirplane(airplane);
        Messages messages= new Messages();
        messages.setResponseCode(201);
        messages.setResponseMessage("Berhasil Ditambahkan");
        return ResponseEntity.ok().body(messages);
    }

    @Operation(summary="Menampilkan Pesawat")
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
    @GetMapping("/get-airplane")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Messages> get(){
        Messages messages= new Messages();
        messages.setResponseCode(200);
        messages.setResponseMessage("Berhasil Ditampilkan");
        messages.setData(crudService.findAllAirplanes());
        return ResponseEntity.ok().body(messages);
    }

    @Operation(summary="Mengupdate Data Pesawat")
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
    @PutMapping("/update-airplane/{airplaneId}")
    public ResponseEntity<Messages> update(@PathVariable Integer airplaneId, @RequestBody ReqCreateAirplane airplane){
        Messages messages= new Messages();
        Boolean status = crudService.updateAirplane(airplaneId, airplane);
        if (Boolean.TRUE.equals(status)) {
            crudService.updateAirplane(airplaneId, airplane);
            messages.setResponseCode(200);
            messages.setResponseMessage("Berhasil Diupdate");
        }
        else {
            messages.setResponseCode(204);
            messages.setResponseMessage("Data tidak ditemukan");
        }
        return ResponseEntity.ok().body(messages);
    }

    @Operation(summary="Menghapus Data Pesawat")
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
    @DeleteMapping("/delete-airplane/{airplaneId}")
    public ResponseEntity<Messages> delete(@PathVariable Integer airplaneId){
        Messages messages= new Messages();
        Boolean status = crudService.deleteAirplane(airplaneId);
        if (Boolean.TRUE.equals(status)) {
            crudService.deleteAirplane(airplaneId);
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

