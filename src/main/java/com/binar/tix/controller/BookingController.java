package com.binar.tix.controller;

import com.binar.tix.entities.Orders;
import com.binar.tix.payload.*;
import com.binar.tix.service.BookingService;
import com.binar.tix.service.InitializeService;
import com.binar.tix.utility.Constant;
import com.binar.tix.utility.HttpUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        if (req.getDepartureDate().isBefore(LocalDate.now()) || (req.getReturnDate() != null && req.getReturnDate().isBefore(req.getDepartureDate()))) {
            return ResponseEntity.status(Constant.BAD_REQUEST).body(new Messages(Constant.BAD_REQUEST, "Setidaknya pilih tanggal hari ini"));
        } else {
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
        String status = bookingService.createOrder(req, userId, null);
        Messages resp = new Messages();
        if (status.length() > 0) {
            List<RespCreateOrder> list = new ArrayList<>();
            RespCreateOrder resp1 = new RespCreateOrder(status, "Pemesanan tiket berhasil");
            list.add(resp1);
            if (req.getReturnDate() != null) {
                RespScheduleReturn returnSchedule = bookingService.getScheduleReturn(req.getScheduleId(), req.getReturnDate());
                req.setScheduleId(returnSchedule.getScheduleId());
                String returnStatus = bookingService.createOrder(req, userId, returnSchedule);
                RespCreateOrder resp2;
                if (returnStatus.length() > 0) {
                    resp2 = new RespCreateOrder(returnStatus, "Pemesanan tiket berhasil");
                }else{
                    resp2 = new RespCreateOrder(returnStatus, "Pemesanan tiket gagal, kursi sudah terisi");
                }
                list.add(resp2);
            }

            resp.success();
            resp.setData(list);
        } else {
            resp.setResponseCode(Constant.ALREADY_EXIST);
            resp.setResponseMessage("Kursi sudah terisi");
        }
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }


    @GetMapping(value = "/history")
    public ResponseEntity<Messages> historyBooking(@RequestParam(name = "limit") int limit, @RequestParam(name = "pageNumber") int pageNumber, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("limit : " + limit + " , pageNumber : " + pageNumber));
        log.info(writeLog);

        Integer userId = (Integer) httpServletRequest.getAttribute(Constant.USER_ID);
        if (limit < 1 || pageNumber < 1) {
            return ResponseEntity.status(Constant.BAD_REQUEST).body(new Messages(Constant.BAD_REQUEST, "limit dan pageNumber invalid"));
        } else {
            Messages resp = new Messages();
            Pageable paging = PageRequest.of(pageNumber - 1, limit, Sort.by("paymentDate").descending());
            Page<Orders> data = bookingService.historyBooking(userId, paging);
            if (!data.isEmpty()) {
                PagingResponse dataPaging = new PagingResponse();
                dataPaging.setTotalData(data.getTotalElements());
                dataPaging.setTotalPaging(data.getTotalPages());

                List<RespHistoryBooking> list = new ArrayList<>();
                for (Orders o : data.getContent()) {
                    RespHistoryBooking h = new RespHistoryBooking();
                    h.setInvoiceNo(o.getInvoiceNo());
                    if (o.getTitle().equals("Tuan")) {
                        h.setBookingBy("Tn. " + o.getBookingBy());
                    } else {
                        h.setBookingBy("Ny. " + o.getBookingBy());
                    }

                    h.setPaymentDate(o.getPaymentDate());
                    h.setPaymentName(o.getPayment().getPaymentMethod());
                    h.setTotalPerson(o.getOrdersDetails().size());
                    h.setClassType(o.getSchedule().getClassSeats().getName());
                    String destination1 = o.getSchedule().getDestination().getDepartureCity().getCityName();
                    String destination2 = o.getSchedule().getDestination().getDestinationsCity().getCityName();
                    h.setDestination(destination1 + " - " + destination2);
                    h.setAmount(o.getAmount());
                    list.add(h);
                }

                resp.setPaging(dataPaging);
                resp.setData(list);
                resp.success();
            } else {
                resp.notFound();
            }
            String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
            log.info(writeLogResp);
            return ResponseEntity.ok().body(resp);
        }
    }

    @GetMapping(value = "/history-detail/{invoiceNo}")
    public ResponseEntity<Messages> detailHistory(@PathVariable(name = "invoiceNo") String invoiceNo, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(invoiceNo));
        log.info(writeLog);

        Messages resp = bookingService.detailHistory(invoiceNo);
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }
}
