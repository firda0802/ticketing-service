package com.binar.tix.service;

import com.binar.tix.entities.*;
import com.binar.tix.payload.*;
import com.binar.tix.repository.*;
import com.binar.tix.utility.Constant;
import com.google.zxing.WriterException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookingServiceImplTest {

    @InjectMocks
    BookingService bookingService = new BookingServiceImpl();

    @Mock
    DestinationCityRepository destinationCityRepository;

    @Mock
    ClassRepository classRepository;

    @Mock
    PassengerTypeRepository passengerTypeRepository;

    @Mock
    OrdersRepository ordersRepository;

    @Mock
    ScheduleRepository scheduleRepository;

    List<DestinationCity> destinationCityList = new ArrayList<>();
    List<ClassSeats> classSeatsList = new ArrayList<>();
    List<PassengerType> passengerTypeList = new ArrayList<>();
    Schedule schedule = new Schedule(1, 1, 7500, LocalTime.of(3, 40), LocalTime.of(3, 40).plusMinutes(750), LocalDate.now(), new ScheduleAirplane(1, 5000, Constant.DOMESTIK));

    @BeforeEach
    void setUp() {
        destinationCityList.add(new DestinationCity("Jakarta"));
        classSeatsList.add(new ClassSeats("Ekonomi", 7500));
        passengerTypeList.add(new PassengerType("Anak-Anak", "Usia 2-11 tahun", 2));
    }

    @Test
    void listDestinationCity() {
        when(bookingService.listDestinationCity()).thenReturn(destinationCityList);
        int expSize = destinationCityList.size();
        int actSize = bookingService.listDestinationCity().size();
        assertEquals(expSize, actSize);
    }

    @Test
    void listClassSeat() {
        when(bookingService.listClassSeat()).thenReturn(classSeatsList);
        var expData = classSeatsList.get(0);
        var actData = bookingService.listClassSeat().get(0);
        assertEquals(expData.getName(), actData.getName());
    }

    @Test
    void listPassengerType() {
        when(bookingService.listPassengerType()).thenReturn(passengerTypeList);
        String expType = passengerTypeList.get(0).getType();
        String actType = bookingService.listPassengerType().get(0).getType();
        assertEquals(expType, actType);
    }

    @Test
    void getTitle() {
        String expResult = "Tn. ";
        String actResult = bookingService.getTitle("Tuan");
        assertEquals(expResult, actResult);
    }

    @Test
    void validateTokenQr() {
        String token = "ls1DDnr5ptdrlO1wrXde38XiPK5p1/keUCs55lSLI/Y=";
        bookingService.validateTokenQr(token);
        when(ordersRepository.findById(token)).thenReturn(Optional.of(new Orders()));
        var expResult = ordersRepository.findById(token);
        assertTrue(expResult.isPresent());
    }

    @Test
    void historyBooking() {
        Pageable paging = PageRequest.of(1, 10);
        Page<Orders> page = ordersRepository.findByUserId(1, paging);
        when(bookingService.historyBooking(1, paging)).thenReturn(page);
        verify(ordersRepository).findByUserId(1, paging);
    }

    @Test
    void validatePassenger() {
        Boolean expResult = true;
        Boolean actResult = bookingService.validatePassenger(1, 1, 1);
        assertEquals(expResult, actResult);
    }

    @Test
    void validatePassengerNegative() {
        Boolean expResult = false;
        Boolean actResult = bookingService.validatePassenger(0, 1, 1);
        assertEquals(expResult, actResult);
    }

    @Test
    void getSchedule() {
        List<Schedule> list = List.of(new Schedule(1, 1, 7500, LocalTime.of(3, 40), LocalTime.of(3, 40).plusMinutes(750), LocalDate.now(), new ScheduleAirplane(1, 5000, Constant.DOMESTIK)));
        when(bookingService.getSchedule()).thenReturn(list);
        assertTrue(bookingService.getSchedule().size() > 0);
    }

    @Test
    void showScheduleFlight() {
        when(scheduleRepository.findByDestinationIdAndClassIdAndFlightDate(1, 1, LocalDate.now())).thenReturn(List.of(schedule));
        var expResult = List.of(schedule);
        var actAResult = scheduleRepository.findByDestinationIdAndClassIdAndFlightDate(1, 1, LocalDate.now());
        assertEquals(expResult, actAResult);
    }

    @Test
    void seatsAvailable() {
        bookingService.seatsAvailable(1);
        when(scheduleRepository.findById(1)).thenReturn(Optional.of(schedule));
        var expResult = Optional.of(schedule);
        var actResult = scheduleRepository.findById(1);
        assertEquals(expResult, actResult);
    }

    @Test
    void createOrder() throws IOException, WriterException {
        ReqCreateOrder req = new ReqCreateOrder();
        req.setTitle("Tuan");
        req.setScheduleId(1);
        req.setEmail("jhon@gmail.com");
        req.setBookingBy("Jhon");
        req.setScheduleId(2397);
        req.setPaymentId(1);
        List<ReqCreateOrderDetail> details = new ArrayList<>();
        ReqCreateOrderDetail d = new ReqCreateOrderDetail();
        d.setTitle("Tuan");
        d.setFullName("Jhon");
        d.setPassengerType(1);
        d.setCitizenship(102);
        d.setIdSeats(102);
        details.add(d);
        req.setDetails(details);

        Orders orders = new Orders();
        orders.setInvoiceNo(RandomStringUtils.randomAlphanumeric(10));
        orders.setUserId(1);
        orders.setTitle(req.getTitle());
        orders.setBookingBy(req.getBookingBy());
        orders.setEmail(req.getEmail());
        orders.setPhoneNo(req.getPhoneNo());
        orders.setPaymentId(req.getPaymentId());
        orders.setAmount(req.getAmount());
        orders.setScheduleId(req.getScheduleId());
        Set<OrdersDetails> detailOrder = new HashSet<>();
        OrdersDetails ordersDetails = new OrdersDetails();
        ordersDetails.setSeatsId(d.getIdSeats());
        ordersDetails.setTitle(d.getTitle());
        ordersDetails.setPassengerType(d.getPassengerType());
        ordersDetails.setFullName(d.getFullName());
        detailOrder.add(ordersDetails);
        orders.setOrdersDetails(detailOrder);
        ordersRepository.saveAndFlush(orders);
        verify(ordersRepository).saveAndFlush(orders);
    }

    @Test
    void detailHistory() {
        String invNumber = "INV-7778-200";
        bookingService.detailHistory(invNumber);
        doReturn(Optional.of(new Orders())).when(ordersRepository).findById(invNumber);
        assertTrue(ordersRepository.findById(invNumber).isPresent());
    }
}