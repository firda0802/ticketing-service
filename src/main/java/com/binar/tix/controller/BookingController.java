package com.binar.tix.controller;

import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqCreateOrder;
import com.binar.tix.payload.ReqShowBooking;
import com.binar.tix.service.BookingService;
import com.binar.tix.service.InitializeService;
import com.binar.tix.utility.Constant;
import com.binar.tix.utility.HttpUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    InitializeService initService;
    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping(value = "/destination-city")
    public ResponseEntity<Messages> destinationCity(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);
        Messages resp = new Messages();
        resp.success();
        resp.setData(bookingService.listDestinationCity());
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping(value = "/class-seats")
    public ResponseEntity<Messages> classSeats(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);

        Messages resp = new Messages();
        resp.success();
        resp.setData(bookingService.listClassSeat());
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping(value = "/passenger_type")
    public ResponseEntity<Messages> passengerType(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);

        Messages resp = new Messages();
        resp.success();
        resp.setData(bookingService.listPassengerType());
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping(value = "/payment_type")
    public ResponseEntity<Messages> paymentType(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);

        Messages resp = new Messages();
        resp.success();
        resp.setData(initService.listPayment());
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping(value = "/citizenship")
    public ResponseEntity<Messages> citizenship(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);

        Messages resp = new Messages();
        resp.success();
        resp.setData(initService.listCitizenship());
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @PostMapping(value = "/schedule-available")
    public ResponseEntity<Messages> scheduleAvailable(@RequestBody ReqShowBooking req, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(req));
        log.info(writeLog);

        if(req.getDepartureDate().isBefore(LocalDate.now()) || (req.getReturnDate() != null && req.getReturnDate().isBefore(req.getDepartureDate()))){
            return ResponseEntity.status(Constant.BAD_REQUEST).body(new Messages(Constant.BAD_REQUEST, "Setidaknya pilih tanggal hari ini"));
        }else{
            Messages resp = bookingService.showScheduleFlight(req);
            String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
            log.info(writeLogResp);
            return ResponseEntity.ok().body(resp);
        }


    }

    @GetMapping(value = "/chose-seats/{scheduleId}")
    public ResponseEntity<Messages> seatsAvailable(@PathVariable(name = "scheduleId") int scheduleId, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(scheduleId));
        log.info(writeLog);

        Messages resp = bookingService.seatsAvailable(scheduleId);
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @PostMapping(value = "/create-order")
    public ResponseEntity<Messages> createOrder(@RequestBody ReqCreateOrder req, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(req));
        log.info(writeLog);

        Integer userId = (Integer) httpServletRequest.getAttribute(Constant.USER_ID);
        String status = bookingService.createOrder(req, userId);
        Messages resp = new Messages();
        if (status.length() > 0) {
            resp.success();
        } else {
            resp.setResponseCode(Constant.ALREADY_EXIST);
            resp.setResponseMessage("Kursi sudah terisi");
        }
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }
}
