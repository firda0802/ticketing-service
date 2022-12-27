package com.binar.tix.controller;

import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqCreatePayment;
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
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    CrudService paymentService;

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Menambahkan Pembayaran",
                            description = "Penambahan metode pembayaran selain yang sudah tersedia - oleh ADMIN",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Berhasil Ditambahkan\"\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
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

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Menampilkan Metode Pembayaran",
                            description = "Menampilkan semua metode pembayaran yang tersedia untuk proses pembelian tiket.",
                            value ="{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Berhasil Ditampilkan\",\n" +
                                    "    \"data\": [\n" +
                                    "        {\n" +
                                    "            \"paymentId\": 1,\n" +
                                    "            \"paymentMethod\": \"Link Aja\",\n" +
                                    "            \"status\": true\n" +
                                    "        }" +
                                    "    ]\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @GetMapping("/get-payment")
    public ResponseEntity<Messages> get(){
        Messages messages= new Messages();
        messages.success();
        messages.setResponseCode(200);
        messages.setResponseMessage("Berhasil Ditampilkan");
        messages.setData(paymentService.findAllPayment());
        return ResponseEntity.ok().body(messages);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Mengupdate Metode Pembayaran",
                            description = "Update metode pembayaran dilakukan jika ada perubahan yang diinginkan pada metode pembayaran tertentu - oleh ADMIN.",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Berhasil Diupdate\"\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
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

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Menghapus Metode Pembayaran",
                            description = "Hapus suatu metode pembayaran apabila sudah tidak tersedia - oleh ADMIN berdasarkan id nya.",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Berhasil Dihapus\"\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
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
