package com.binar.tix.controller;

import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqCreateSeats;
import com.binar.tix.service.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/seats")
public class SeatsController {

    @Autowired
    CrudService seatsService;

    @Operation(summary="Menambahkan Kursi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Kursi berhasil ditambahkan.",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))}),
            @ApiResponse(responseCode = "400", description = "Eror dari sisi Browser .",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))}),
            @ApiResponse(responseCode = "500", description = "Eror dari sisi Server.",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))})})
    @PostMapping("/add-seats")
    public ResponseEntity<Messages> create(@RequestBody ReqCreateSeats seats){
        seatsService.saveSeats(seats);
        Messages messages= new Messages();
        messages.setResponseCode(201);
        messages.setResponseMessage("Berhasil Ditambahkan");
        return ResponseEntity.ok().body(messages);
    }

    @Operation(summary="Menampilkan Kursi")
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
    @GetMapping("/get-seats")
    public ResponseEntity<Messages> get(){
        Messages messages= new Messages();
        messages.setResponseCode(200);
        messages.setResponseMessage("Berhasil Ditampilkan");
        messages.setData(seatsService.findAllSeats());
        return ResponseEntity.ok().body(messages);
    }

    @Operation(summary="Mengupdate Data Kursi")
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
    @PutMapping("/update-seats/{seatsId}")
    public ResponseEntity<Messages> update(@PathVariable Integer seatsId, @RequestBody ReqCreateSeats seats){
        Messages messages= new Messages();
        Boolean status = seatsService.updateSeats(seatsId,seats);
        if (Boolean.TRUE.equals(status)) {
            seatsService.updateSeats(seatsId,seats);
            messages.setResponseCode(200);
            messages.setResponseMessage("Berhasil Diupdate");
        }
        else {
            messages.setResponseCode(204);
            messages.setResponseMessage("Data tidak ditemukan");
        }
        return ResponseEntity.ok().body(messages);
    }

    @Operation(summary="Menghapus Data Kursi")
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
    @DeleteMapping("delete-seats/{seatsId}")
    public ResponseEntity<Messages> delete(@PathVariable Integer seatsId){
        Messages messages= new Messages();
        Boolean status = seatsService.deleteSeats(seatsId);
        if (Boolean.TRUE.equals(status)) {
            seatsService.deleteSeats(seatsId);
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
