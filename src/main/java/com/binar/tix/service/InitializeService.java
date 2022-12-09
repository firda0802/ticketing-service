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

    void initPricing(Pricing req);
    List<Airport> getAirport();
    void initAirport(Airport airport);
    void initAirplanes(Airplane airplane);

    List<Airplane> getAirplane();
    void initClassSeats(ClassSeats classSeats);

    void initDestinationCity(DestinationCity destinationCity);

    List<DestinationCity> getDestinationCity();
    void initDestination(List<Destination> destination);

    List<Destination> getDestination();

    void initPassengerType(PassengerType req);

    List<PassengerType> getPassengerType();

    void initSchedule(List<Schedule> req);

    void initPayment(Payment payment);
    List<Payment> listPayment();

    List<Citizenship> listCitizenship();

    void initCitizenship(Citizenship req);

    List<Facility> listFacility();

    void initFacility(List<Facility> req);
}
