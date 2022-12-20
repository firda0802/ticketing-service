package com.binar.tix.controller;

import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqCreatePayment;
import com.binar.tix.service.PaymentService;
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
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Operation(summary="Menambahkan Pembayaran")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pembayaran berhasil ditambahkan.",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))}),
            @ApiResponse(responseCode = "400", description = "Eror dari sisi Browser .",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))}),
            @ApiResponse(responseCode = "500", description = "Eror dari sisi Server.",
                    content = {@Content(mediaType="application/json",
                            schema = @Schema(implementation = Response.class))})})
    @PostMapping("/add-payment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Messages> create(@RequestBody ReqCreatePayment payment){
        paymentService.savePayment(payment);
        Messages messages= new Messages();
        messages.success();
        messages.setResponseCode(201);
        messages.setResponseMessage("Berhasil Ditambahkan");
        return ResponseEntity.ok().body(messages);
    }

    @Operation(summary="Menampilkan Pembayaran")
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
    @GetMapping("/get-payment")
    public ResponseEntity<Messages> get(){
        Messages messages= new Messages();
        messages.success();
        messages.setResponseCode(200);
        messages.setResponseMessage("Berhasil Ditampilkan");
        messages.setData(paymentService.findAllPayment());
        return ResponseEntity.ok().body(messages);
    }

    @Operation(summary="Mengupdate Data Pembayaran")
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
    @PutMapping("/update-payment/{paymentId}")
    public ResponseEntity<Messages> update(@PathVariable Integer paymentId, @RequestBody ReqCreatePayment payment){
        Messages messages= new Messages();
        Boolean status = paymentService.updatePayment(paymentId, payment);
        if (Boolean.TRUE.equals(status)) {
            paymentService.updatePayment(paymentId, payment);
            messages.setResponseCode(200);
            messages.setResponseMessage("Berhasil Diupdate");
        }
        else {
            messages.setResponseCode(204);
            messages.setResponseMessage("Data tidak ditemukan");
        }
        return ResponseEntity.ok().body(messages);
    }

    @Operation(summary="Menghapus Data Pembayaran")
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
    @DeleteMapping("/delete-payment/{paymentId}")
    public ResponseEntity<Messages> delete(@PathVariable Integer paymentId){
        Messages messages= new Messages();
        Boolean status = paymentService.deletePayment(paymentId);
        if (Boolean.TRUE.equals(status)) {
            paymentService.deletePayment(paymentId);
            messages.success();
            messages.setResponseCode(200);
            messages.setResponseMessage("Berhasil Dihapus");
        }
        else {
            messages.notFound();
            messages.setResponseCode(204);
            messages.setResponseMessage("Data tidak ditemukan");
        }
        return ResponseEntity.ok().body(messages);
    }
}
