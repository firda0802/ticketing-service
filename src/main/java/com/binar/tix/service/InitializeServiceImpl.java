package com.binar.tix.service;

import com.binar.tix.entities.*;
import com.binar.tix.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitializeServiceImpl implements InitializeService {

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
    public List<ClassSeats> listClass() {
        return classRepository.findAll();
    }

    @Override
    public Pricing initPricing(Pricing req) {
       return pricingRepository.saveAndFlush(req);
    }

    @Override
    public List<Airport> getAirport() {
        return airportRepository.findAll();
    }

    @Override
    public Airport initAirport(Airport airport) {
        return airportRepository.saveAndFlush(airport);
    }

    @Override
    public Airplane initAirplanes(Airplane airplane) {
        return airplaneRepository.saveAndFlush(airplane);
    }

    @Override
    public List<Airplane> getAirplane() {
        return airplaneRepository.findAll();
    }

    @Override
    public ClassSeats initClassSeats(ClassSeats classSeats) {
        return classRepository.saveAndFlush(classSeats);
    }

    @Override
    public DestinationCity initDestinationCity(DestinationCity destinationCity) {
        return destinationCityRepository.saveAndFlush(destinationCity);
    }

    @Override
    public List<DestinationCity> getDestinationCity() {
        return destinationCityRepository.findAll();
    }

    @Override
    public List<Destination> initDestination(List<Destination> destination) {
        return destinationRepository.saveAll(destination);
    }

    @Override
    public List<Destination> getDestination() {
        return destinationRepository.findAll();
    }

    @Override
    public PassengerType initPassengerType(PassengerType req) {
        return passengerTypeRepository.saveAndFlush(req);
    }

    @Override
    public List<PassengerType> getPassengerType() {
        return passengerTypeRepository.findAll();
    }

    @Override
    public List<Schedule> initSchedule(List<Schedule> req) {
        return scheduleRepository.saveAll(req);
    }

    @Override
    public Payment initPayment(Payment payment) {
        payment.setStatus(true);
        return paymentRepository.saveAndFlush(payment);
    }

    @Override
    public List<Payment> listPayment() {
        return paymentRepository.findByStatus(Boolean.TRUE);
    }

    @Override
    public List<Citizenship> listCitizenship() {
        return citizenshipRepository.findAll();
    }

    @Override
    public Citizenship initCitizenship(Citizenship req) {
       return citizenshipRepository.saveAndFlush(req);
    }

    @Override
    public List<Facility> listFacility() {
        return facilityRepository.findAll();
    }

    @Override
    public List<Facility> initFacility(List<Facility> req) {
        return facilityRepository.saveAll(req);
    }

    @Override
    public List<Users> dataAdmin() {
        return usersRepository.findByRoleId(2);
    }

    @Override
    public Users initAdmin(Users users) {
        return usersRepository.saveAndFlush(users);
    }
}
