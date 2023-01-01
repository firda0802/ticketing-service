package com.binar.tix.service;

import com.binar.tix.entities.*;
import com.binar.tix.payload.ScheduleAirplane;
import com.binar.tix.repository.*;
import com.binar.tix.utility.Constant;
import com.binar.tix.utility.MD5;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InitializeServiceImplTest {

    @InjectMocks
    InitializeService initializeService = new InitializeServiceImpl();

    @Mock
    ClassRepository classRepository;

    @Mock
    PricingRepository pricingRepository;

    @Mock
    AirplaneRepository airplaneRepository;

    @Mock
    AirportRepository airportRepository;

    @Mock
    DestinationCityRepository destinationCityRepository;

    @Mock
    DestinationRepository destinationRepository;

    @Mock
    PassengerTypeRepository passengerTypeRepository;

    @Mock
    FacilityRepository facilityRepository;

    @Mock
    UsersRepository usersRepository;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    CitizenshipRepository citizenshipRepository;

    Users admin = new Users();

    @BeforeEach
    void setUp() {
        admin.setEmail("admin@safly.com");
        admin.setPassword(MD5.encrypt("admin123"));
        admin.setStatus(Boolean.TRUE);
        admin.setRoleId(2);
        admin.setFullName("Safly Admin");
    }

    @Test
    void listClassTes() {
        List<ClassSeats> classSeatsList = List.of(new ClassSeats("Ekonomi", 7500));
        when(initializeService.listClass()).thenReturn(classSeatsList);
        assertTrue(initializeService.listClass().size() > 0);
    }

    @Test
    void listClassNegativeTest() {
        when(initializeService.listClass()).thenReturn(new ArrayList<>());
        List<ClassSeats> classSeatsList = initializeService.listClass();
        Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> classSeatsList.get(0));
        assertEquals("Index 0 out of bounds for length 0", exception.getMessage());
    }

    @Test
    void initPricingTest() {
        Pricing pricing = new Pricing(25000);
        when(initializeService.initPricing(pricing)).thenReturn(pricing);
        int expectedPrice = pricing.getPrice();
        int actualPrice = initializeService.initPricing(pricing).getPrice();
        assertEquals(expectedPrice, actualPrice);

    }

    @Test
    void initAirplanes() {
        Airplane airplane = new Airplane("Boeing 77", 20, 1);
        when(initializeService.initAirplanes(airplane)).thenReturn(airplane);
        String expectedName = airplane.getType();
        String actualName = initializeService.initAirplanes(airplane).getType();
        assertEquals(expectedName, actualName);
    }

    @Test
    void getAirportTest() {
        List<Airport> airports = List.of(new Airport("Soekarno Hatta", "Jalan Jakarta", 1));
        when(initializeService.getAirport()).thenReturn(airports);
        assertTrue(initializeService.getAirport().size() > 0);
    }

    @Test
    void initAirport() {
        Airport airport = new Airport("King Abdul Aziz", "Mesir", 8);
        when(initializeService.initAirport(airport)).thenReturn(airport);
        String airportNameExpected = airport.getName();
        String airportNameActual = initializeService.initAirport(airport).getName();
        assertEquals(airportNameExpected, airportNameActual);
    }

    @Test
    void getAirplane() {
        List<Airplane> airplane = List.of(new Airplane("Boeing A07", 50, 1));
        when(initializeService.getAirplane()).thenReturn(airplane);
        assertFalse(initializeService.getAirplane().isEmpty());
    }

    @Test
    void initClassSeats() {
        ClassSeats classSeats = new ClassSeats("Business", 7500);
        when(initializeService.initClassSeats(classSeats)).thenReturn(classSeats);
        assertEquals(classSeats.getPrice(), initializeService.initClassSeats(classSeats).getPrice());
    }

    @Test
    void initDestinationCity() {
        DestinationCity destinationCity = new DestinationCity("Bandung");
        when(initializeService.initDestinationCity(destinationCity)).thenReturn(destinationCity);
        List<DestinationCity> list = new ArrayList<>();
        list.add(destinationCity);
        assertEquals(1, list.size());
    }

    @Test
    void getDestinationCity() {
        List<DestinationCity> list = List.of(new DestinationCity("Jakarta"));
        when(initializeService.getDestinationCity()).thenReturn(list);
        assertEquals(list.size(), initializeService.getDestinationCity().size());
    }

    @Test
    void initDestination() {
        List<Destination> list = List.of(new Destination(1, 2, 56500, 750));
        when(initializeService.initDestination(list)).thenReturn(list);
        int expectedJumlah = list.size();
        int actualJumlah = initializeService.initDestination(list).size();
        assertEquals(expectedJumlah, actualJumlah);

    }

    @Test
    void getDestination() {
        List<Destination> list = List.of(new Destination(3, 2, 1500, 150));
        when(initializeService.getDestination()).thenReturn(list);
        int expectedPrice = list.get(0).getAdditionalPrice();
        int actualPrice = initializeService.getDestination().get(0).getAdditionalPrice();
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void initPassengerType() {
        PassengerType passengerType = new PassengerType("Anak-anak", "Untuk anak umur 3-12 tahun",2);
        when(initializeService.initPassengerType(passengerType)).thenReturn(passengerType);
        String expectedType = passengerType.getType();
        String actualType = initializeService.initPassengerType(passengerType).getType();
        assertEquals(expectedType, actualType);
    }

    @Test
    void getPassengerType() {
        List<PassengerType> list = List.of(new PassengerType("Anak-anak", "Untuk anak umur 3-12 tahun",2), new PassengerType("Dewasa", "Usia di atas 12 tahun",1));
        when(initializeService.getPassengerType()).thenReturn(list);
        assertEquals(list.size(), initializeService.getPassengerType().size());
    }


    @Test
    void initFacility() {
        List<Facility> facilities = Arrays.asList(new Facility("Tersedia makanan dan minuman", 1), new Facility("Terdapat asisten pribadi", 3));
        when(initializeService.initFacility(facilities)).thenReturn(facilities);
        assertTrue(initializeService.initFacility(facilities).size() > 0);

    }


    @Test
    void listFacility() {
        List<Facility> facilities = List.of(new Facility("Tersedia makanan dan minuman", 1));
        when(initializeService.listFacility()).thenReturn(facilities);
        String expectedFacility = facilities.get(0).getFacilityName();
        String actualFacility = initializeService.listFacility().get(0).getFacilityName();
        assertEquals(expectedFacility, actualFacility);

    }

    @Test
    void dataAdmin() {
        List<Users> usersList = List.of(admin);
        when(initializeService.dataAdmin()).thenReturn(usersList);
        assertEquals(admin.getEmail(), initializeService.dataAdmin().get(0).getEmail());
    }

    @Test
    void initAdmin() {
        when(initializeService.initAdmin(admin)).thenReturn(admin);
        assertEquals(admin.getRoleId(), initializeService.initAdmin(admin).getRoleId());
    }

    @Test
    void initSchedule() {
        Schedule schedule = new Schedule(1, 2, 3500, LocalTime.of(3, 40), LocalTime.of(3, 40).plusMinutes(750), LocalDate.now(), new ScheduleAirplane(1, 5000, Constant.DOMESTIK));
        List<Schedule> list = List.of(schedule);
        when(initializeService.initSchedule(list)).thenReturn(list);
        assertEquals(list.size(), initializeService.initSchedule(list).size());
    }

    @Test
    void initPayment() {
        Payment payment = new Payment("DANA");
        when(initializeService.initPayment(payment)).thenReturn(payment);
        String expectedPayment = payment.getPaymentMethod();
        String actualPayment = initializeService.initPayment(payment).getPaymentMethod();
        assertEquals(expectedPayment, actualPayment);
    }

    @Test
    void listPayment() {
        List<Payment> list = List.of(new Payment("DANA"));
        when(initializeService.listPayment()).thenReturn(list);
        assertTrue(initializeService.listPayment().size() > 0);
    }

    @Test
    void listCitizenship() {
        List<Citizenship> list = List.of(new Citizenship("Indonesia","ID"), new Citizenship("Afghanistan","AF"));
        when(initializeService.listCitizenship()).thenReturn(list);
        int expectedSize = list.size();
        int actualSize = initializeService.listCitizenship().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void initCitizenship() {
        Citizenship citizenship = new Citizenship("Indonesia", "ID");
        when(initializeService.initCitizenship(citizenship)).thenReturn(citizenship);
        assertEquals(citizenship, initializeService.initCitizenship(citizenship));

    }
}