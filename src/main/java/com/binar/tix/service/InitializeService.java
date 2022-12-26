/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.service;

import com.binar.tix.entities.*;

import java.util.List;

/**
 *
 * @author Riko
 */
public interface InitializeService {

    List<ClassSeats> listClass();
    Pricing initPricing(Pricing req);
    List<Airport> getAirport();
    Airport initAirport(Airport airport);
    Airplane initAirplanes(Airplane airplane);

    List<Airplane> getAirplane();
    ClassSeats initClassSeats(ClassSeats classSeats);

    DestinationCity initDestinationCity(DestinationCity destinationCity);

    List<DestinationCity> getDestinationCity();
    List<Destination> initDestination(List<Destination> destination);

    List<Destination> getDestination();

    PassengerType initPassengerType(PassengerType req);

    List<PassengerType> getPassengerType();

    List<Schedule> initSchedule(List<Schedule> req);

    Payment initPayment(Payment payment);
    List<Payment> listPayment();

    List<Citizenship> listCitizenship();

    Citizenship initCitizenship(Citizenship req);

    List<Facility> listFacility();

    List<Facility> initFacility(List<Facility> req);

    List<Users> dataAdmin();

    Users initAdmin(Users users);
}
