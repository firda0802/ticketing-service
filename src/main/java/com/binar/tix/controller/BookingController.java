package com.binar.tix.controller;

import com.binar.tix.entities.Orders;
import com.binar.tix.payload.*;
import com.binar.tix.service.BookingService;
import com.binar.tix.service.InitializeService;
import com.binar.tix.utility.Constant;
import com.binar.tix.utility.HttpUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    InitializeService initService;
    private final ObjectMapper mapper = new ObjectMapper();

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "DestinationCity", description = "Menampilkan nama kota untuk penerbangan berdasarkan id.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "    \"data\": \"destinationCityId\": 1, \n" +
                            "cityName\": Jakarta\"\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @GetMapping(value = "/destination-city")
    public ResponseEntity<Messages> destinationCity(HttpServletRequest httpServletRequest)
            throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);
        Messages resp = new Messages();
        resp.success();
        resp.setData(bookingService.listDestinationCity());
        String writeLogResp = HttpUtility.writeLogResp(
                mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Class List", description = "Menampilkan list kelas beserta nama dan harga untuk para penumpang berdasarkan id.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "    \"data\": \"classId\": 1, \n" +
                            "name\": Economi Class\"\n" +
                            "price\": 35.000\"\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @GetMapping(value = "/class-seats")
    public ResponseEntity<Messages> classSeats(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);

        Messages resp = new Messages();
        resp.success();
        resp.setData(bookingService.listClassSeat());
        String writeLogResp = HttpUtility.writeLogResp(
                mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "PassengerType", description = "Menampilkan type penumpang dan deskripsi untuk penerbangan berdasarkan id.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "    \"data\": \"passengertypeId\": 1, \n" +
                            "type\": Anak - Anak\"\n" +
                            "description\": Umur 2 - 11 Tahun\"\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @GetMapping(value = "/passenger_type")
    public ResponseEntity<Messages> passengerType(HttpServletRequest httpServletRequest)
            throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);

        Messages resp = new Messages();
        resp.success();
        resp.setData(bookingService.listPassengerType());
        String writeLogResp = HttpUtility.writeLogResp(
                mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "PaymentType", description = "Menampilkan jenis pembayaran yang dilakukan", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "    \"data\": \"paymentId\": 30, \n" +
                            "paymentMethod\": DANA\"\n" +
                            "status\": true\"\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @GetMapping(value = "/booking/payment_type")
    public ResponseEntity<Messages> paymentType(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);

        Messages resp = new Messages();
        resp.success();
        resp.setData(initService.listPayment());
        String writeLogResp = HttpUtility.writeLogResp(
                mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "citizenship", description = "Menampilkan kewarganegaraan berdasarkan kode.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "    \"data\": \"Id\": 1, \n" +
                            "name\": Afghanistan\"\n" +
                            "code\": AF\"\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @GetMapping(value = "/booking/citizenship")
    public ResponseEntity<Messages> citizenship(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString("-"));
        log.info(writeLog);

        Messages resp = new Messages();
        resp.success();
        resp.setData(initService.listCitizenship());
        String writeLogResp = HttpUtility.writeLogResp(
                mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "ScheduleAvailable", description = "Menampilkan jadwal penerbangan yang tersedia untuk setiap hari nya dan silahkan mengatur hari terlebih dahulu sebelum digunakan.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "    \"data\": \"city\": [\n" +
                            "{\n" +
                            "Id\": 10, \n" +
                            "classId\": \"Jakarta\", \n" +
                            "facilityName\": \"Memiliki asister first class yang akan mengurus semua hal\" }\n" +
                            "]\n" +

                            "schedule\": [\n" +
                            "{\n" +
                            "flight\": \"international\", \n" +
                            "scheduleId\": 312581, \n" +
                            "classType\": \"First Class\", \n" +
                            "luggageCapacity\": \"50KG\", \n" +
                            "departureCity\": \"Jakarta\",\n" +
                            "destinationsCity\": \"Jeddah\", \n" +
                            "airplane\": \"Boeing 777-300ER\", \n" +
                            "airport\": \"Bandar Udara Internasional Soekarno–Hatta\", \n" +
                            "airportAddress\": \"Kota Tangerang, Banten 19120\", \n" +
                            "startTime\": \"07:30\", \n" +
                            "endTime\": \"19:45\", \n" +
                            "price\": 72676250,}\n" +
                            "]\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @PostMapping(value = "/schedule-available")
    public ResponseEntity<Messages> scheduleAvailable(@RequestBody ReqShowBooking req,
            HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(req));
        log.info(writeLog);

        if (req.getDepartureDate().isBefore(LocalDate.now())
                || (req.getReturnDate() != null && req.getReturnDate().isBefore(req.getDepartureDate()))) {
            return ResponseEntity.status(Constant.BAD_REQUEST)
                    .body(new Messages(Constant.BAD_REQUEST, "Setidaknya pilih tanggal hari ini"));
        } else {
            Messages resp = bookingService.showScheduleFlight(req);
            String writeLogResp = HttpUtility.writeLogResp(
                    mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
            log.info(writeLogResp);
            return ResponseEntity.ok().body(resp);
        }

    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "SeatAvailable", description = "Menampilkan kursi yang tersedia, jika bersetatus BOOKED maka kursi tidak dapat diorder dan jika berstatus AVAILABLE maka kursi tersedia.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "    \"data\": \"groupSeats\" : \"A\", \n" +
                            "{, \n" +
                            "seatList\": [\n" +
                            "seatsId\": 22, \n" +
                            "seatsNumber\": \"A1\", \n" +
                            "statusSeats\": \"BOOKED\", \n" +
                            "canBook\": \"true\", \n" +
                            "}, \n" +

                            "{, \n" +
                            "seatList\": [\n" +
                            "seatsId\": 45, \n" +
                            "seatsNumber\": \"A3\", \n" +
                            "statusSeats\": \"AVAILABLE\", \n" +
                            "canBook\": \"true\", \n" +
                            "}, \n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @GetMapping(value = "/booking/chose-seats/{scheduleId}")
    public ResponseEntity<Messages> seatsAvailable(@PathVariable(name = "scheduleId") int scheduleId,
            HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(scheduleId));
        log.info(writeLog);

        Messages resp = bookingService.seatsAvailable(scheduleId);
        String writeLogResp = HttpUtility.writeLogResp(
                mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "CreateOrder", description = "Dapat Melakukan order pada penerbangan.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @PostMapping(value = "/booking/create-order")
    public ResponseEntity<Messages> createOrder(@RequestBody ReqCreateOrder req, HttpServletRequest httpServletRequest)
            throws WriterException, IOException {
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
                RespScheduleReturn returnSchedule = bookingService.getScheduleReturn(req.getScheduleId(),
                        req.getReturnDate());
                req.setScheduleId(returnSchedule.getScheduleId());
                String returnStatus = bookingService.createOrder(req, userId, returnSchedule);
                RespCreateOrder resp2;
                if (returnStatus.length() > 0) {
                    resp2 = new RespCreateOrder(returnStatus, "Pemesanan tiket berhasil");
                } else {
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
        String writeLogResp = HttpUtility.writeLogResp(
                mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Hystory", description = "Menampilkan history dari transaksi user.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "    \"data\": [\n" +
                            "{\n" +
                            "invoiceId\": \"INV-CVX-221219-32626\", \n" +
                            "paymentDate\": 2022-12-19 03:26:27, \n" +
                            "bookingBy\": \"Tn.Riko\", \n" +
                            "totalPerson\": 1, \n" +
                            "destination\": \"Jakarta-Jeddah\", \n" +
                            "classType\": \"First Class\", \n" +
                            "paymentName\": \"Transfer Bank BCA\", \n" +
                            "amount\": 72676250,\n" +
                            "}\n" +
                            "]\n" +
                            "pahing\" : {\n" +
                            "totalData\" : 1, \n" +
                            "totalPaging\" : 1, \n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @GetMapping(value = "/booking/history")
    public ResponseEntity<Messages> historyBooking(@RequestParam(name = "limit") int limit,
            @RequestParam(name = "pageNumber") int pageNumber, HttpServletRequest httpServletRequest)
            throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest,
                mapper.writeValueAsString("limit : " + limit + " , pageNumber : " + pageNumber));
        log.info(writeLog);

        Integer userId = (Integer) httpServletRequest.getAttribute(Constant.USER_ID);
        if (limit < 1 || pageNumber < 1) {
            return ResponseEntity.status(Constant.BAD_REQUEST)
                    .body(new Messages(Constant.BAD_REQUEST, "limit dan pageNumber invalid"));
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
            String writeLogResp = HttpUtility.writeLogResp(
                    mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
            log.info(writeLogResp);
            return ResponseEntity.ok().body(resp);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "HystoryDetail", description = "Menampilkan history detail dari transaksi user.", value = "{\n"
                            +
                            "    \"responseCode\": 200,\n" +
                            "    \"responseMessage\": \"Sukses\",\n" +
                            "    \"data\": [\n" +
                            "{\n" +
                            "invoiceId\": \"INV-CVX-221219-32626\", \n" +
                            "paymentDate\": 2022-12-19 03:26:27, \n" +
                            "bookingBy\": \"Tn.Riko\", \n" +
                            "totalPerson\": 1, \n" +
                            "destination\": \"Jakarta-Jeddah\", \n" +
                            "classType\": \"First Class\", \n" +
                            "paymentName\": \"Transfer Bank BCA\", \n" +
                            "amount\": 72676250,\n" +
                            "amount\": \"https://res.cloudinary.com/dsbm2cqxn/image/upload/v1670827496/fty8lbgtotodl2tzb3e7\",\n"
                            +
                            "}\n" +
                            "]\n" +
                            "pahing\" : {\n" +
                            "totalData\" : 1, \n" +
                            "totalPaging\" : 1, \n" +
                            "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)) })
    @GetMapping(value = "/booking/history-detail/{invoiceNo}")
    public ResponseEntity<Messages> detailHistory(@PathVariable(name = "invoiceNo") String invoiceNo,
            HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(invoiceNo));
        log.info(writeLog);

        Messages resp = bookingService.detailHistory(invoiceNo);
        String writeLogResp = HttpUtility.writeLogResp(
                mapper.writeValueAsString(new Messages(resp.getResponseCode(), resp.getResponseMessage())));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping(value = "/validate-token/{tokenQr}")
    public ResponseEntity<Messages> validateToken(@PathVariable(name = "tokenQr") String tokenQr,
            HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String writeLog = HttpUtility.writeLogRequest(httpServletRequest, mapper.writeValueAsString(tokenQr));
        log.info(writeLog);

        boolean status = bookingService.validateTokenQr(tokenQr);
        Messages resp = new Messages();
        if (status) {
            resp.setResponseCode(Constant.OK);
            resp.setResponseMessage("Verified Success Token");
        } else {
            resp.setResponseCode(Constant.NO_CONTENT);
            resp.setResponseMessage("Token is Invalid");
        }
        String writeLogResp = HttpUtility.writeLogResp(mapper.writeValueAsString(resp));
        log.info(writeLogResp);
        return ResponseEntity.ok().body(resp);
    }
}
