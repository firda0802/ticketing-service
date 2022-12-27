package com.binar.tix.controller;

import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqCreateSeats;
import com.binar.tix.service.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/seats")
public class SeatsController {

    @Autowired
    CrudService seatsService;

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Menambahkan Kursi",
                            description = "Penambahan kursi penumpang yang tersedia pada suatu pesawat",
                            value = "{\n" +
                                    "    \"responseCode\": 201,\n" +
                                    "    \"responseMessage\": \"Berhasil Ditambahkan\"\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @PostMapping("/add-seats")
    public ResponseEntity<Messages> create(@RequestBody ReqCreateSeats seats){
        seatsService.saveSeats(seats);
        Messages messages= new Messages();
        messages.setResponseCode(201);
        messages.setResponseMessage("Berhasil Ditambahkan");
        return ResponseEntity.ok().body(messages);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Menampilkan Data Kursi",
                            description = "Menampilkan semua data kursi yang tersedia pada suatu pesawat",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Berhasil Ditampilkan\",\n" +
                                    "    \"data\": [\n" +
                                    "        {\n" +
                                    "            \"seatsId\": 1,\n" +
                                    "            \"seatsNumber\": \"D15\",\n" +
                                    "            \"seatsGroup\": \"D\",\n" +
                                    "            \"positions\": 15,\n" +
                                    "            \"classId\": 2,\n" +
                                    "            \"airplanesId\": 1,\n" +
                                    "            \"classSeats\": {\n" +
                                    "                \"classId\": 2,\n" +
                                    "                \"name\": \"Business Class\",\n" +
                                    "                \"price\": 100000\n" +
                                    "            }\n"+
                                    "       }\n"+
                                    "    ]\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @GetMapping("/get-seats")
    public ResponseEntity<Messages> get(){
        Messages messages= new Messages();
        messages.setResponseCode(200);
        messages.setResponseMessage("Berhasil Ditampilkan");
        messages.setData(seatsService.findAllSeats());
        return ResponseEntity.ok().body(messages);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Mengupdate Data Kursi",
                            description = "Update data kursi untuk perubahan yang diinginkan pada data kursi tertentu.",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Berhasil Diupdate\"\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
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

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Menghapus Data Kursi",
                            description = "Hapus data kursi untuk menghapus kursi yang sudah tidak tersedia lagi di suatu pesawat berdasarkan id nya",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Berhasil Dihapus\"\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
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
