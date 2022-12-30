package com.binar.tix.controller;

import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqCreateAirplane;
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
@RequestMapping("/airplane")
public class AirplaneController {

    @Autowired
    CrudService crudService;

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Menambahkan Airplane", description = "Airplane bisa ditambahkan jika ingin ada data baru airplane",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Berhasil Ditambahkan\"\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @PostMapping("/add-airplane")
    public ResponseEntity<Messages> create(@RequestBody ReqCreateAirplane airplane){
        crudService.saveAirplane(airplane);
        Messages messages= new Messages();
        messages.setResponseCode(201);
        messages.setResponseMessage("Berhasil Ditambahkan");
        return ResponseEntity.ok().body(messages);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Menampilkan Pesawat", description = "",
                            value = "{\n" +
                                    "   \"responseCode\":200,\n" +
                                    "   \"responseMessage\":\"Berhasil Ditampilkan\",\n" +
                                    "   \"data\":[\n" +
                                    "      {\n" +
                                    "         \"airplaneId\":1,\n" +
                                    "         \"type\":\"Boeing 777-300ER\",\n" +
                                    "         \"luggageCapacity\":50,\n" +
                                    "         \"airportId\":1,\n" +
                                    "         \"airport\":{\n" +
                                    "            \"idAirport\":1,\n" +
                                    "            \"name\":\"Bandar Udara Internasional Soekarno–Hatta\",\n" +
                                    "            \"address\":\"Jl. Soetta\",\n" +
                                    "            \"cityId\":4,\n" +
                                    "            \"city\":{\n" +
                                    "               \"destinationCityId\":4,\n" +
                                    "               \"cityName\":\"Makassar\"\n" +
                                    "            }\n" +
                                    "         }\n" +
                                    "      }\n" +
                                    "   ]\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @GetMapping("/get-airplane")
    public ResponseEntity<Messages> get(){
        Messages messages= new Messages();
        messages.setResponseCode(200);
        messages.setResponseMessage("Berhasil Ditampilkan");
        messages.setData(crudService.findAllAirplanes());
        return ResponseEntity.ok().body(messages);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Mengupdate Data Airplane", description = "Untuk mengubah data airplane, pastikan id yang dimasukan benar dan data yang diubah bisa diatur sesuai dengan kebutuhan.",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Berhasil Diupdate\"\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
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

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Menghapus Data Airplane", description = "Data airplane bisa dihapus dengan memasukan id yang valid yang hendak ingin dihapus",
                            value = "{\n" +
                                    "    \"responseCode\": 200,\n" +
                                    "    \"responseMessage\": \"Berhasil Dihapus\"\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
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

