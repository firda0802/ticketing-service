package com.binar.tix.service;

import com.binar.tix.entities.*;
import com.binar.tix.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class InitializeServiceImpl implements InitializeService{

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    DestinationRepository destinationRepository;
    @Autowired
    DestinationCityRepository destinationCityRepository;

    @Autowired
    AirplaneRepository airplaneRepository;

    @Autowired
    ClassRepository classRepository;

    @Autowired
    PassengerTypeRepository passengerTypeRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PricingRepository pricingRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CitizenshipRepository citizenshipRepository;

    @Autowired
    FacilityRepository facilityRepository;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public void initPricing(Pricing req) {
        pricingRepository.saveAndFlush(req);
    }

    @Override
    public List<Airport> getAirport() {
        return airportRepository.findAll();
    }

    @Override
    public void initAirport(Airport airport) {
        airportRepository.saveAndFlush(airport);
    }

    @Override
    public void initAirplanes(Airplane airplane) {
        airplaneRepository.saveAndFlush(airplane);
    }

    @Override
    public List<Airplane> getAirplane() {
        return airplaneRepository.findAll();
    }

    @Override
    public void initClassSeats(ClassSeats classSeats) {
        classRepository.saveAndFlush(classSeats);
    }

    @Override
    public void initDestinationCity(DestinationCity destinationCity) {
        destinationCityRepository.saveAndFlush(destinationCity);
    }

    @Override
    public List<DestinationCity> getDestinationCity() {
        return destinationCityRepository.findAll();
    }

    @Override
    public void initDestination(List<Destination> destination) {
        destinationRepository.saveAll(destination);
    }

    @Override
    public List<Destination> getDestination() {
        return destinationRepository.findAll();
    }

    @Override
    public void initPassengerType(PassengerType req) {
        passengerTypeRepository.saveAndFlush(req);
    }

    @Override
    public List<PassengerType> getPassengerType() {
        return passengerTypeRepository.findAll();
    }

    @Override
    public void initSchedule(List<Schedule> req) {
        scheduleRepository.saveAll(req);
    }

    @Override
    public void initPayment(Payment payment) {
        payment.setStatus(true);
        paymentRepository.saveAndFlush(payment);
    }

    @Override
    public List<Payment> listPayment() {
        return paymentRepository.findByStatus(Boolean.TRUE);
    }

    @Override
    public List<Citizenship> listCitizenship() {
        List<Citizenship> list = citizenshipRepository.findAll();
        if(!list.isEmpty()){
            list.sort(Comparator.comparing(Citizenship::getName));
        }
        return list;
    }

    @Override
    public void initCitizenship(Citizenship req) {
        citizenshipRepository.saveAndFlush(req);
    }

    @Override
    public List<Facility> listFacility() {
        return facilityRepository.findAll();
    }

    @Override
    public void initFacility(List<Facility> req) {
        facilityRepository.saveAll(req);
    }

    @Override
    public List<Users> dataAdmin() {
        return usersRepository.findByRoleId(2);
    }

    @Override
    public void initAdmin(Users users) {
        usersRepository.saveAndFlush(users);
    }
}
