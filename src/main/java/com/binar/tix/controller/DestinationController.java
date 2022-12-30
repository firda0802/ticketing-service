package com.binar.tix.controller;

import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqUpdateDestination;
import com.binar.tix.service.CrudService;
import com.binar.tix.service.InitializeService;
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

@RestController
@RequestMapping("/dest")
@Slf4j
public class DestinationController {

    @Autowired
    InitializeService initializeService;

    @Autowired
    CrudService destinationService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Destination List", description = "Menampilkan seluruh destinasi yang tersedia didalam aplikasi.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "    \"data\": \"destinationId\": 1, \n" +
                            "departure\": 1,\n" +
                            "departureCity\": \"destinationCityId\": 1," + "cityName\": \"Jakarta\", \n" +
                            "destinations\": 10,\n" +
                            "departureCity\": \"destinationCityId\": 10," + "cityName\": \"Tokyo\", \n" +
                            "additionalPrice\": 21723750,\n" +
                            "duration\": 435,\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @GetMapping(value = "/get-list")
    public ResponseEntity<Messages> listDestination(HttpServletRequest httpServletRequest)
            throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);
        Messages resp = new Messages();
        resp.success();
        resp.setData(initializeService.getDestination());
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(resp));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Add Update Destination", description = "Dapat Melakukan update dan menambahkan destinasi di aplikasi.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @PostMapping(value = "/add-update")
    public ResponseEntity<Messages> addUpdateDestination(@RequestBody ReqUpdateDestination req,
            HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(req));
        log.info(writeLog);
        boolean status = destinationService.updateAddDestination(req);
        Messages resp = new Messages();
        if (status) {
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
                    @ExampleObject(name = "Delete Destination", description = "Dapat menghapus destinasi jika id tersedia.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @DeleteMapping(value = "/delete/{destId}")
    public ResponseEntity<Messages> deleteClass(@PathVariable(name = "destId") Integer destId,
            HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(destId));
        log.info(writeLog);
        Messages resp = new Messages();
        Boolean status = destinationService.deleteDestination(destId);
        if (Boolean.TRUE.equals(status)) {
            resp.success();
        } else {
            resp.notFound();
        }
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(resp));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }
}
