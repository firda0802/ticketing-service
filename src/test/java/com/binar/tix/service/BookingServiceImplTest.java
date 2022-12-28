package com.binar.tix.service;

import com.binar.tix.entities.ClassSeats;
import com.binar.tix.entities.DestinationCity;
import com.binar.tix.entities.PassengerType;
import com.binar.tix.repository.ClassRepository;
import com.binar.tix.repository.DestinationCityRepository;
import com.binar.tix.repository.PassengerTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    List<DestinationCity> destinationCityList = new ArrayList<>();
    List<ClassSeats> classSeatsList = new ArrayList<>();
    List<PassengerType> passengerTypeList = new ArrayList<>();
    @BeforeEach
    void setUp() {
        destinationCityList.add(new DestinationCity("Jakarta"));
        classSeatsList.add(new ClassSeats("Ekonomi",7500));
        passengerTypeList.add(new PassengerType("Anak-Anak", "Usia 2-11 tahun"));
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

//    @Test
//    void validateTokenQr() {
//    }
//
//
//
//    @Test
//    void historyBooking() {
//    }
//
//    @Test
//    void getSchedule() {
//    }
//
//    @Test
//    void showScheduleFlight() {
//    }
//
//    @Test
//    void seatsAvailable() {
//    }
//
//    @Test
//    void dataSeats() {
//    }
//
//    @Test
//    void createOrder() {
//    }
//
//    @Test
//    void getScheduleReturn() {
//    }
//
//    @Test
//    void detailHistory() {
//    }
}