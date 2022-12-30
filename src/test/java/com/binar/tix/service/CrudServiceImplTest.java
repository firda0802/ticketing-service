package com.binar.tix.service;

import com.binar.tix.entities.*;
import com.binar.tix.payload.*;
import com.binar.tix.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Mock
    SeatsRepository seatsRepository;

    @Mock
    ClassRepository classRepository;

    @Mock
    DestinationRepository destinationRepository;

    Schedule schedule = new Schedule();

    Seats seats = new Seats();

    ClassSeats classSeats = new ClassSeats();

    Destination destination = new Destination();

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
        Boolean result = crudService.updateAirplane(5, req);
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
        crudService.updateAirport(4, "Kualanamu", "Medan", 4);
        doReturn(Optional.of(airport)).when(airportRepository).findById(4);
        verify(airportRepository).findById(4);
    }

    @Test
    void updateAirportNegativeTest() {
        airport.setIdAirport(2);
        Boolean result = crudService.updateAirport(5, "Kualanamu", "Medan", 2);
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

    // @Test
    // void savePaymentNegativeTest() {
    // }

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
        Boolean result = crudService.updatePayment(5, req);
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
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(new Schedule());
        given(scheduleRepository.getSchedule(10,0)).willReturn(schedules);
        List<Schedule> expected = crudService.findAllSchedule(10,0);
        assertEquals(expected, schedules);
        verify(scheduleRepository).getSchedule(10,0);
    }

    /**
     * 
     */
    @Test
    void findAllScheduleNegativeTest() {
        when(crudService.findAllSchedule(10,0)).thenReturn(new ArrayList<>());
        List<Schedule> schedules = crudService.findAllSchedule(10,0);
        Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> schedules.get(0));
        assertEquals("Index 0 out of bounds for length 0", exception.getMessage());
    }

    @Test
    void saveSchedulePositiveTest() {
        ReqCreateSchedule req = new ReqCreateSchedule();
        req.setDestinationId(1);
        req.setClassId(3);
        req.setPrice((int) 785.000);
        req.setAirplanesId(33);
        req.setFlight("international");
        schedule.setDestinationId(req.getDestinationId());
        schedule.setClassId(req.getClassId());
        schedule.setPrice(req.getPrice());
        schedule.setAirplanesId(req.getAirplanesId());
        schedule.setFlight(req.getFlight());

        crudService.saveSchedule(req);
        doReturn(schedule).when(scheduleRepository).saveAndFlush(schedule);
        assertEquals(schedule.getDestinationId(), scheduleRepository.saveAndFlush(schedule).getDestinationId());
        assertEquals(schedule.getClassId(), scheduleRepository.saveAndFlush(schedule).getClassId());
        assertEquals(schedule.getPrice(), scheduleRepository.saveAndFlush(schedule).getPrice());
        assertEquals(schedule.getAirplanesId(), scheduleRepository.saveAndFlush(schedule).getAirplanesId());
        assertEquals(schedule.getFlight(), scheduleRepository.saveAndFlush(schedule).getFlight());
    }

    // @Test
    // void saveScheduleNegativeTest(){

    // }

    @Test
    void updateSchedulePositiveTest() {
        ReqCreateSchedule req = new ReqCreateSchedule();
        req.setDestinationId(1);
        req.setClassId(3);
        req.setPrice((int) 785.000);
        req.setAirplanesId(33);
        req.setFlight("international");
        schedule.setDestinationId(req.getDestinationId());
        schedule.setClassId(req.getClassId());
        schedule.setPrice(req.getPrice());
        schedule.setAirplanesId(req.getAirplanesId());
        schedule.setFlight(req.getFlight());
        schedule.setScheduleId(311833);

        crudService.updateSchedule(311833, req);
        doReturn(Optional.of(schedule)).when(scheduleRepository).findById(311833);
        verify(scheduleRepository).findById(311833);
    }

    @Test
    void updateScheduleNegativeTest() {
        ReqCreateSchedule req = new ReqCreateSchedule();
        schedule.setScheduleId(311833);
        Boolean result = crudService.updateSchedule(500000, req);
        assertEquals(false, result);
    }

    @Test
    void deleteSchedulePositiveTest() {
        CrudService mockInstance = mock(CrudService.class);
        when(mockInstance.deleteSchedule(311833)).thenReturn(true);
        assertEquals(true, mockInstance.deleteSchedule(311833));
    }

    @Test
    void deleteScheduleNegativeTest() {
        schedule.setScheduleId(2);
        Boolean result = crudService.deleteSchedule(500000);
        System.out.println(result);
        assertEquals(false, result);
    }

    @Test
    void findAllSeatsPositiveTest() {
        List<Seats> seats = new ArrayList();
        seats.add(new Seats());
        given(seatsRepository.findAll()).willReturn(seats);
        List<Seats> expected = crudService.findAllSeats();
        assertEquals(expected, seats);
        verify(seatsRepository).findAll();
    }

    @Test
    void findAllSeatsNegativeTest(){
        when(crudService.findAllSeats()).thenReturn(new ArrayList<>());
        List<Seats> seats = crudService.findAllSeats();
        Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> seats.get(0));
        assertEquals("Index 0 out of bounds for length 0", exception.getMessage());
    }

    @Test
    void saveSeatsPositiveTest() {
        ReqCreateSeats req = new ReqCreateSeats();
        req.setSeatsGroup("A");
        req.setSeatsNumber("A2");
        req.setPositions(2);
        req.setClassId(3);
        req.setAirplanesId(33);
        req.setClassSeats("First Class");
        seats.setSeatsGroup(req.getSeatsGroup());
        seats.setSeatsNumber(req.getSeatsNumber());
        seats.setPositions(req.getPositions());
        seats.setClassId(req.getClassId());
        seats.setAirplanesId(req.getAirplanesId());
        seats.setClassSeats(req.getClassSeats());

        crudService.saveSeats(req);
        doReturn(seats).when(seatsRepository).saveAndFlush(seats);
        assertEquals(seats.getSeatsGroup(), seatsRepository.saveAndFlush(seats).getSeatsGroup());
        assertEquals(seats.getSeatsNumber(), seatsRepository.saveAndFlush(seats).getSeatsNumber());
        assertEquals(seats.getPositions(), seatsRepository.saveAndFlush(seats).getPositions());
        assertEquals(seats.getClassId(), seatsRepository.saveAndFlush(seats).getClassId());
        assertEquals(seats.getAirplanesId(), seatsRepository.saveAndFlush(seats).getAirplanesId());
        assertEquals(seats.getClassSeats(), seatsRepository.saveAndFlush(seats).getClassSeats());
    }
    // @Test
    // void saveSeatsNegativeTest(){

    // }
    @Test
    void updateSeatsPositiveTest() {
        ReqCreateSeats req = new ReqCreateSeats();
        req.setSeatsGroup("A");
        req.setSeatsNumber("A2");
        req.setPositions(2);
        req.setClassId(3);
        req.setAirplanesId(33);
        req.setClassSeats("First Class");
        seats.setSeatsGroup(req.getSeatsGroup());
        seats.setSeatsNumber(req.getSeatsNumber());
        seats.setPositions(req.getPositions());
        seats.setClassId(req.getClassId());
        seats.setAirplanesId(req.getAirplanesId());
        seats.setClassSeats(req.getClassSeats());
        seats.setSeatsId(req.getSeatsId(1));

        crudService.updateSeats(1, req);
        doReturn(Optional.of(seats)).when(seatsRepository).findById(1);
        verify(seatsRepository).findById(1);
    }

    @Test
    void updateSeatsNegativeTest() {
        ReqCreateSeats req = new ReqCreateSeats();
        seats.setSeatsId(2);
        Boolean result = crudService.updateSeats(2000, req);
        assertEquals(false, result);
    }

    @Test
    void deleteSeatsPositiveTest() {
        CrudService mockInstance = mock(CrudService.class);
        when(mockInstance.deleteSeats(1)).thenReturn(true);
        assertEquals(true, mockInstance.deleteSeats(1));
    }

    @Test
    void deleteSeatsNegativeTest() {
        seats.setSeatsId(2);
        Boolean result = crudService.deleteSeats(2000);
        System.out.println(result);
        assertEquals(false, result);
    }

    @Test
    void updateAddClassPositiveTest() {
        ReqUpdateClass req = new ReqUpdateClass();
        req.setName("Economi Class");
        req.setPrice(35000);
        crudService.updateAddClass(req);
        classSeats.setName(req.getName());
        classSeats.setPrice(req.getPrice());
        doReturn(classSeats).when(classRepository).saveAndFlush(classSeats);
        assertEquals(classSeats, classRepository.saveAndFlush(classSeats));
    }

    @Test
    void updateAddClassNegativeTest() {
        ReqUpdateClass req = new ReqUpdateClass();
        req.setId(88);
        req.setName("Economi Class");
        req.setPrice(35000);
        classSeats.setClassId(2);
        Boolean result = crudService.updateAddClass(req);
        assertEquals(false, result);
    }

    @Test
    void deleteClassSeatsPositiveTest() {
        CrudService mockInstance = mock(CrudService.class);
        when(mockInstance.deleteClassSeats(1)).thenReturn(true);
        assertEquals(true, mockInstance.deleteClassSeats(1));
    }

    @Test
    void deleteClassSeatsNegativeTest() {
        classSeats.setClassId(2);
        Boolean result = crudService.deleteClassSeats(4);
        System.out.println(result);
        assertEquals(false, result);
    }

    @Test
    void updateAddDestinationPositiveTest() {
        ReqUpdateDestination req = new ReqUpdateDestination();
        req.setId(1);
        req.setDuration(250);
        req.setPrice(7500);
        req.setDestinationCityId(1);
        req.setDepartureCityId(2);
        crudService.updateAddDestination(req);
        doReturn(Optional.of(destination)).when(destinationRepository).findById(1);
        verify(destinationRepository).findById(1);
    }

    @Test
    void updateAddDestinationNegativeTest() {
        destination.setDestinationId(2);
        ReqUpdateDestination req = new ReqUpdateDestination();
        req.setId(75);
        req.setDuration(250);
        req.setPrice(7500);
        req.setDestinationCityId(1);
        req.setDepartureCityId(2);
        Boolean result = crudService.updateAddDestination(req);
        assertEquals(false, result);
    }

    @Test
    void deleteDestinationPositiveTest() {
        CrudService mockInstance = mock(CrudService.class);
        when(mockInstance.deleteDestination(1)).thenReturn(true);
        assertEquals(true, mockInstance.deleteDestination(1));
    }

    @Test
    void deleteDestinationNegativeTest() {
        destination.setDestinationId(2);
        Boolean result = crudService.deleteDestination(50);
        System.out.println(result);
        assertEquals(false, result);
    }
}