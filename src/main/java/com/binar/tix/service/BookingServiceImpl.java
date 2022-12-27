package com.binar.tix.service;

import com.binar.tix.entities.*;
import com.binar.tix.enums.StatusEnum;
import com.binar.tix.payload.*;
import com.binar.tix.repository.*;
import com.binar.tix.utility.Constant;
import com.binar.tix.utility.QrCode;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.jasypt.util.text.StrongTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Autowired
    DestinationRepository destinationRepository;
    @Autowired
    DestinationCityRepository destinationCityRepository;

    @Autowired
    ClassRepository classRepository;

    @Autowired
    PassengerTypeRepository passengerTypeRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    FacilityRepository facilityRepository;

    @Autowired
    SeatsRepository seatsRepository;

    @Override
    public List<DestinationCity> listDestinationCity() {
        return destinationCityRepository.findAll();
    }

    @Override
    public List<ClassSeats> listClassSeat() {
        return classRepository.findAll();
    }

    @Override
    public List<PassengerType> listPassengerType() {
        return passengerTypeRepository.findAll();
    }

    @Override
    public List<Schedule> getSchedule() {
        return scheduleRepository.findAll();
    }

    @Override
    public Messages showScheduleFlight(ReqShowBooking req) {
        Messages msg = new Messages();
        msg.setResponseCode(Constant.NO_CONTENT);
        msg.setResponseMessage("Tidak ada jadwal penerbangan");
        RespSchedule respSchedule = new RespSchedule();
        Optional<Destination> cekDestination = destinationRepository.findByDepartureAndDestinations(req.getDepartureCity(), req.getDestinationsCity());
        if (cekDestination.isPresent()) {
            Destination destination = cekDestination.get();
            List<Schedule> schedules = scheduleRepository.findByDestinationIdAndClassIdAndFlightDate(destination.getDestinationId(), req.getClassSeats(), req.getDepartureDate());
            if (!schedules.isEmpty()) {
                respSchedule.setFacility(facilityRepository.findByClassId(req.getClassSeats()));
                List<RespScheduleData> list = new ArrayList<>();
                for (Schedule s : schedules) {
                    RespScheduleData data = new RespScheduleData();
                    data.setFlight(s.getFlight());
                    data.setScheduleId(s.getScheduleId());
                    data.setClassType(s.getClassSeats().getName());
                    if (data.getClassType().equals("Business Class")) {
                        data.setLuggageCapacity(s.getAirplane().getLuggageCapacity() + 10 + "KG");
                    } else {
                        data.setLuggageCapacity(s.getAirplane().getLuggageCapacity() + "KG");
                    }
                    data.setDepartureCity(destination.getDepartureCity().getCityName());
                    data.setDestinationsCity(destination.getDestinationsCity().getCityName());
                    data.setAirplane(s.getAirplane().getType());
                    data.setAirport(s.getAirplane().getAirport().getName());
                    data.setAirportAddress(s.getAirplane().getAirport().getAddress());
                    data.setStartTime(s.getStartTime());
                    data.setEndTime(s.getEndTime());
                    data.setPrice(s.getPricing().getPrice() + destination.getAdditionalPrice() + s.getPrice());
                    list.add(data);
                }
                respSchedule.setSchedule(list);
                msg.success();
                msg.setData(respSchedule);
            }
        }

        return msg;
    }


    @Override
    public Messages seatsAvailable(int scheduleId) {
        Messages msg = new Messages();
        List<DataSeats> resp = new ArrayList<>();
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleId);
        if (schedule.isPresent()) {
            Schedule sch = schedule.get();
            for (Seats s : sch.getAirplane().getSeats()) {
                DataSeats r = new DataSeats();
                r.setSeatsNumber(s.getSeatsNumber());
                r.setSeatsGroup(s.getSeatsGroup() + "");
                r.setClassSeats(s.getClassId());
                r.setPosition(s.getPositions());
                r.setSeatsId(s.getSeatsId());
                StatusSeats checkSeats = ordersRepository.checkSeats(scheduleId, r.getSeatsId());
                if (checkSeats.getStatus() > 0) {
                    r.setStatus(StatusEnum.BOOKED.name());
                } else {
                    r.setStatus(StatusEnum.AVAILABLE.name());
                }

                resp.add(r);
            }
            msg.success();
            msg.setData(dataSeats(resp, sch.getClassId()));
        } else {
            msg.notFound();
        }
        return msg;
    }

    @Override
    public List<RespSeats> dataSeats(List<DataSeats> list, int classId) {
        List<RespSeats> resp = new ArrayList<>();
        Map<String, List<DataSeats>> myData = list.stream().collect(Collectors.groupingBy(DataSeats::getSeatsGroup));
        for (Map.Entry<String, List<DataSeats>> seats : myData.entrySet()) {
            String groupSeats = seats.getKey();
            seats.getValue().sort(Comparator.comparing(DataSeats::getPosition));
            RespSeats data = new RespSeats();
            data.setGroupSeats(groupSeats);
            List<RespSeatsDetail> detail = new ArrayList<>();
            for (DataSeats d : seats.getValue()) {
                RespSeatsDetail det = new RespSeatsDetail();
                det.setSeatsId(d.getSeatsId());
                det.setCanBook(d.getClassSeats() == classId);
                det.setSeatsNumber(d.getSeatsNumber());
                det.setStatusSeats(d.getStatus());
                detail.add(det);
            }
            data.setSeatsList(detail);
            resp.add(data);
        }
        return resp;
    }

    @Override
    public String createOrder(ReqCreateOrder req, int userId, RespScheduleReturn scheduleReturn) throws WriterException, IOException {
        StringBuilder checkStatus = new StringBuilder();
        Date date = new Date();
        Format formatter1 = new SimpleDateFormat("yyMMdd");
        Format formatter2 = new SimpleDateFormat("Hms");
        String s1 = formatter1.format(date);
        String s2 = formatter2.format(date);
        String invoice = "INV-" + RandomStringUtils.randomAlphabetic(3).toUpperCase() + "-" + s1 + "-" + s2;

        Orders orders = new Orders();
        orders.setInvoiceNo(invoice);
        orders.setUserId(userId);
        orders.setTitle(req.getTitle());
        orders.setBookingBy(req.getBookingBy());
        orders.setEmail(req.getEmail());
        orders.setPhoneNo(req.getPhoneNo());
        orders.setPaymentId(req.getPaymentId());
        orders.setAmount(req.getAmount());
        orders.setScheduleId(req.getScheduleId());
        Set<OrdersDetails> detailOrder = new HashSet<>();

        for (ReqCreateOrderDetail d : req.getDetails()) {
            OrdersDetails detail = new OrdersDetails();
            detail.setTitle(d.getTitle());
            detail.setFullName(d.getFullName());
            if (scheduleReturn == null) {
                detail.setSeatsId(d.getIdSeats());
            } else {
                Seats seats = seatsRepository.findById(d.getIdSeats()).orElse(null);
                assert seats != null;
                Seats returnSeats = seatsRepository.findByClassIdAndAirplanesIdAndSeatsNumber(scheduleReturn.getClassId(), scheduleReturn.getAirplaneId(), seats.getSeatsNumber());
                detail.setSeatsId(returnSeats.getSeatsId());
            }

            detail.setCitizenshipId(d.getCitizenship());
            detail.setPassengerType(d.getPassengerType());

            if (d.getPassportNumber() != null && !d.getPassportNumber().equals("")) {
                detail.setPassportNumber(d.getPassportNumber());
                detail.setIssuingCountry(d.getIssuingCountry());
                detail.setExpirationDate(d.getExpirationDate());
            }

            detailOrder.add(detail);
            StatusSeats checkSeats = ordersRepository.checkSeats(req.getScheduleId(), d.getIdSeats());
            if (checkSeats.getStatus() > 0) {
                checkStatus.append("1");
            }
        }

        orders.setOrdersDetails(detailOrder);

        if (checkStatus.toString().contains("1")) {
            return "";
        } else {
            String resp = "";
            String urlQrCode = QrCode.generate(invoice);
            Optional<Schedule> sch = scheduleRepository.findById(orders.getScheduleId());
            if (sch.isPresent() && urlQrCode.length() > 0) {
                Destination destination = sch.get().getDestination();
                resp = destination.getDepartureCity().getCityName() + " - " + destination.getDestinationsCity().getCityName();
                orders.setQrCodeUrl(urlQrCode);
                ordersRepository.saveAndFlush(orders);
                ReqCreateNotification notif = new ReqCreateNotification();
                notif.setUserId(userId);
                notif.setNotificationCategoryId(2);
                notif.setTitle("Pemesanan Tiket Berhasil");
                notif.setContent("Selamat! Transaksi dengan invoice no. " + invoice + " sukses dilakukan");
                notificationService.createNotifUsers(notif);
            }
            return resp;
        }
    }


    @Override
    public RespScheduleReturn getScheduleReturn(int scheduleId, LocalDate returnDate) {
        RespScheduleReturn resp = new RespScheduleReturn();
        int scheduleIds = 0;
        Optional<Schedule> data = scheduleRepository.findById(scheduleId);
        if (data.isPresent()) {
            Schedule s1 = data.get();
            int departure = s1.getDestination().getDeparture();
            int destination = s1.getDestination().getDestinations();
            Optional<Destination> cekDestination = destinationRepository.findByDepartureAndDestinations(destination, departure);
            if (cekDestination.isPresent()) {
                Destination dst = cekDestination.get();
                Schedule returnSchedule = scheduleRepository.findByDestinationIdAndClassIdAndFlightDateAndStartTimeAndEndTime(dst.getDestinationId(), s1.getClassId(), s1.getFlightDate(), s1.getStartTime(), s1.getEndTime());
                scheduleIds = returnSchedule.getScheduleId();
                resp.setClassId(returnSchedule.getClassId());
                resp.setAirplaneId(returnSchedule.getAirplanesId());
            }
        }
        resp.setScheduleId(scheduleIds);
        return resp;
    }

    @Override
    public Page<Orders> historyBooking(int userId, Pageable paging) {
        return ordersRepository.findByUserId(userId, paging);
    }

    @Override
    public Messages detailHistory(String invoiceNo) {
        Messages msg = new Messages();
        Optional<Orders> orders = ordersRepository.findById(invoiceNo);
        if (orders.isPresent()) {
            Orders o = orders.get();
            msg.success();
            RespHistoryDetail resp = new RespHistoryDetail();
            resp.setInvoiceNo(o.getInvoiceNo());
            resp.setBookingBy(getTitle(o.getTitle()) + o.getBookingBy());
            resp.setAirplane(o.getSchedule().getAirplane().getType());
            resp.setStartTime(o.getSchedule().getStartTime());
            resp.setEndTime(o.getSchedule().getEndTime());
            resp.setFlightDate(o.getSchedule().getFlightDate());
            resp.setPaymentDate(o.getPaymentDate());
            resp.setPaymentName(o.getPayment().getPaymentMethod());
            resp.setTotalPerson(o.getOrdersDetails().size());
            resp.setClassType(o.getSchedule().getClassSeats().getName());
            String destination1 = o.getSchedule().getDestination().getDepartureCity().getCityName();
            String destination2 = o.getSchedule().getDestination().getDestinationsCity().getCityName();
            resp.setDepartureCity(destination1);
            resp.setDestinationsCity(destination2);

            List<RespHistoryDetailtem> detail = new ArrayList<>();
            for (OrdersDetails d : o.getOrdersDetails()) {
                RespHistoryDetailtem rd = new RespHistoryDetailtem();
                rd.setName(getTitle(d.getTitle()) + d.getFullName());
                rd.setType(d.getPassenger().getType());
                rd.setSeatsNumber(d.getSeats().getSeatsNumber());
                if (resp.getClassType().equals("Business Class")) {
                    rd.setLuggageCapacity(o.getSchedule().getAirplane().getLuggageCapacity() + 10);
                } else {
                    rd.setLuggageCapacity(o.getSchedule().getAirplane().getLuggageCapacity());
                }
                if (d.getPassportNumber() != null) {
                    rd.setTravelDocument(new TravelDocument(d.getPassportNumber(), d.getIssuingCountry(), d.getExpirationDate()));
                }
                detail.add(rd);
            }
            resp.setDetail(detail);
            resp.setAmount(o.getAmount());
            msg.setData(resp);
        } else {
            msg.notFound();
        }
        return msg;
    }

    @Override
    public Boolean validateTokenQr(String token) {
        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(Constant.ENCRYPT_KEY);
        try {
            String result = URLDecoder.decode(token, StandardCharsets.UTF_8);
            String decryptToken = textEncryptor.decrypt(result);
            Optional<Orders> data = ordersRepository.findById(decryptToken);
            return data.isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getTitle(String title) {
        if (title.equals("Tuan")) {
            return "Tn. ";
        } else {
            return "Ny. ";
        }
    }
}
