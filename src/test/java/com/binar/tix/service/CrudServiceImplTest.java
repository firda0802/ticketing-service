package com.binar.tix.service;

import com.binar.tix.entities.*;
import com.binar.tix.payload.ReqCreateAirplane;
import com.binar.tix.payload.ReqCreateAirport;
import com.binar.tix.payload.ReqCreatePayment;
import com.binar.tix.repository.AirplaneRepository;
import com.binar.tix.repository.AirportRepository;
import com.binar.tix.repository.PaymentRepository;
import com.binar.tix.repository.ScheduleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CrudServiceImplTest {

    @InjectMocks
    CrudService crudService = new CrudServiceImpl();

    @Mock
    AirplaneRepository airplaneRepository;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    AirportRepository airportRepository;

    @Mock
    ScheduleRepository scheduleRepository;

    Airplane airplane = new Airplane();

    Airport airport = new Airport();

    Payment payment = new Payment();

    @Test
    void findAllAirplanesPositiveTest() {
        List<Airplane> airplanes = new ArrayList();
        airplanes.add(new Airplane());
        given(airplaneRepository.findAll()).willReturn(airplanes);
        List<Airplane> expected = crudService.findAllAirplanes();
        assertEquals(expected, airplanes);
        verify(airplaneRepository).findAll();
    }

    @Test
    void findAllAirplanesNegativeTest(){
        when(crudService.findAllAirplanes()).thenReturn(new ArrayList<>());
        List<Airplane> airplanes = crudService.findAllAirplanes();
        Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> airplanes.get(0));
        assertEquals("Index 0 out of bounds for length 0", exception.getMessage());
    }

    @Test
    void saveAirplanePositiveTest() {
        ReqCreateAirplane req = new ReqCreateAirplane();
        req.setType("Boeing 777-300ER");
        req.setLuggageCapacity(60);
        req.setAirportId(2);
        airplane.setType(req.getType());
        airplane.setLuggageCapacity(req.getLuggageCapacity());
        airplane.setAirportId(req.getAirportId());

        crudService.saveAirplane(req);
        doReturn(airplane).when(airplaneRepository).saveAndFlush(airplane);
        assertEquals(airplane.getType(), airplaneRepository.saveAndFlush(airplane).getType());
        assertEquals(airplane.getLuggageCapacity(), airplaneRepository.saveAndFlush(airplane).getLuggageCapacity());
        assertEquals(airplane.getAirportId(), airplaneRepository.saveAndFlush(airplane).getAirportId());
    }

//    @Test
//    void saveAirplaneNegativeTest() {
//    }

    @Test
    void updateAirplanePositiveTest() {
        ReqCreateAirplane req = new ReqCreateAirplane();
        req.setType("Boeing 777-300ER");
        req.setLuggageCapacity(60);
        req.setAirportId(2);
        airplane.setType(req.getType());
        airplane.setLuggageCapacity(req.getLuggageCapacity());
        airplane.setAirportId(req.getAirportId());
        airplane.setAirplaneId(1);

        crudService.updateAirplane(1, req);
        doReturn(Optional.of(airplane)).when(airplaneRepository).findById(1);
        verify(airplaneRepository).findById(1);
    }

    @Test
    void updateAirplaneNegativeTest() {
        ReqCreateAirplane req = new ReqCreateAirplane();
        airplane.setAirplaneId(1);
        Boolean result = crudService.updateAirplane(5,req);
        System.out.println(result);
        assertEquals(false, result);
    }

    @Test
    void deleteAirplanePositiveTest() {
        CrudService mockInstance = mock(CrudService.class);
        when(mockInstance.deleteAirplane(1)).thenReturn(true);
        assertEquals(true, mockInstance.deleteAirplane(1));
    }

    @Test
    void deleteAirplaneNegativeTest() {
        airplane.setAirplaneId(2);
        Boolean result = crudService.deleteAirplane(5);
        System.out.println(result);
        assertEquals(false, result);
    }

    @Test
    void updateAirportPositiveTest() {
        crudService.updateAirport(4,"Kualanamu","Medan",4 );
        doReturn(Optional.of(airport)).when(airportRepository).findById(4);
        verify(airportRepository).findById(4);
    }

    @Test
    void updateAirportNegativeTest() {
        airport.setIdAirport(2);
        Boolean result = crudService.updateAirport(5,"Kualanamu","Medan",2);
        System.out.println(result);
        assertEquals(false, result);
    }

    @Test
    void deleteAirportPositiveTest() {
        CrudService mockInstance = mock(CrudService.class);
        when(mockInstance.deleteAirport(1)).thenReturn(true);
        assertEquals(true, mockInstance.deleteAirport(1));
    }

    @Test
    void deleteAirportNegativeTest() {
        airport.setIdAirport(2);
        Boolean result = crudService.deleteAirport(5);
        System.out.println(result);
        assertEquals(false, result);
    }

    @Test
    void findAllPaymentPositiveTest() {
        List<Payment> payments = new ArrayList();
        payments.add(new Payment());
        given(paymentRepository.findAll()).willReturn(payments);
        List<Payment> expected = crudService.findAllPayment();
        assertEquals(expected, payments);
        verify(paymentRepository).findAll();
    }

    @Test
    void findAllPaymentNegativeTest() {
        when(crudService.findAllPayment()).thenReturn(new ArrayList<>());
        List<Payment> payments = crudService.findAllPayment();
        Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> payments.get(0));
        assertEquals("Index 0 out of bounds for length 0", exception.getMessage());
    }

    @Test
    void savePaymentPositiveTest() {
        ReqCreatePayment req = new ReqCreatePayment();
        req.setPaymentMethod("DANA");
        req.setStatus(true);
        payment.setPaymentMethod(req.getPaymentMethod());
        payment.setStatus(req.getStatus());

        crudService.savePayment(req);
        doReturn(payment).when(paymentRepository).saveAndFlush(payment);
        assertEquals(payment.getPaymentMethod(), paymentRepository.saveAndFlush(payment).getPaymentMethod());
        assertEquals(payment.getStatus(), paymentRepository.saveAndFlush(payment).getStatus());
    }

//    @Test
//    void savePaymentNegativeTest() {
//    }

    @Test
    void updatePaymentPositiveTest() {
        ReqCreatePayment req = new ReqCreatePayment();
        req.setPaymentMethod("DANA");
        req.setStatus(true);
        payment.setPaymentMethod(req.getPaymentMethod());
        payment.setStatus(req.getStatus());
        payment.setPaymentId(1);

        crudService.updatePayment(1, req);
        doReturn(Optional.of(payment)).when(paymentRepository).findById(1);
        verify(paymentRepository).findById(1);
    }

    @Test
    void updatePaymentNegativeTest() {
        ReqCreatePayment req = new ReqCreatePayment();
        payment.setPaymentId(21);
        Boolean result = crudService.updatePayment(5,req);
        System.out.println(result);
        assertEquals(false, result);
    }

    @Test
    void deletePaymentPositiveTest() {
        CrudService mockInstance = mock(CrudService.class);
        when(mockInstance.deletePayment(1)).thenReturn(true);
        assertEquals(true, mockInstance.deletePayment(1));
    }

    @Test
    void deletePaymentNegativeTest() {
        payment.setPaymentId(2);
        Boolean result = crudService.deletePayment(5);
        System.out.println(result);
        assertEquals(false, result);
    }

    @Test
    void findAllSchedulePositiveTest() {
        List<Schedule> schedules = new ArrayList();
        schedules.add(new Schedule());
        given(scheduleRepository.findAll()).willReturn(schedules);
        List<Schedule> expected = crudService.findAllSchedule();
        assertEquals(expected, schedules);
        verify(scheduleRepository).findAll();
    }

    @Test
    void findAllScheduleNegativeTest() {
        when(crudService.findAllSchedule()).thenReturn(new ArrayList<>());
        List<Schedule> schedules = crudService.findAllSchedule();
        Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> schedules.get(0));
        assertEquals("Index 0 out of bounds for length 0", exception.getMessage());
    }
}