package com.binar.tix.service;

import com.binar.tix.entities.*;
import com.binar.tix.payload.*;
import com.binar.tix.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CrudServiceImpl implements CrudService {

    @Autowired
    AirplaneRepository airplaneRepository;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    SeatsRepository seatsRepository;

    @Autowired
    ClassRepository classRepository;

    @Autowired
    DestinationRepository destinationRepository;

    @Override
    public List<Airplane> findAllAirplanes() {
        return airplaneRepository.findAll();
    }

    @Override
    public Airplane saveAirplane(ReqCreateAirplane req) {
        Airplane airplane1 = new Airplane();
        airplane1.setType(req.getType());
        airplane1.setLuggageCapacity(req.getLuggageCapacity());
        airplane1.setAirportId(req.getAirportId());
        return airplaneRepository.save(airplane1);
    }

    @Override
    public Boolean updateAirplane(Integer airplaneId, ReqCreateAirplane airplane) {
        Optional<Airplane> airplanes = airplaneRepository.findById(airplaneId);
        if (airplanes.isPresent()) {
            Airplane airplane1 = airplanes.get();
            airplane1.setType(airplane.getType());
            airplane1.setLuggageCapacity(airplane.getLuggageCapacity());
            airplane1.setAirportId(airplane.getAirportId());
            airplaneRepository.saveAndFlush(airplane1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteAirplane(Integer airplaneId) {
        Optional<Airplane> airplanes = airplaneRepository.findById(airplaneId);
        if (airplanes.isPresent()) {
            Airplane airplane1= airplanes.get();
            airplaneRepository.delete(airplane1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateAirport(Integer id, String name, String address, int cityId) {
        if (id > 0) {
            Optional<Airport> airports = airportRepository.findById(id);
            if (airports.isPresent()) {
                Airport airport = airports.get();
                airport.setName(name);
                airport.setAddress(address);
                airport.setCityId(cityId);
                airportRepository.saveAndFlush(airport);
                return true;
            } else {
                return false;
            }
        } else {
            Airport newAirport = new Airport();
            newAirport.setName(name);
            newAirport.setAddress(address);
            newAirport.setCityId(cityId);
            airportRepository.saveAndFlush(newAirport);
            return true;
        }

    }

    @Override
    public Boolean deleteAirport(Integer id) {
        Optional<Airport> airport = airportRepository.findById(id);
        if(airport.isPresent()){
            airportRepository.delete(airport.get());
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<Payment> findAllPayment() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment savePayment(ReqCreatePayment payment) {
        Payment payment1 = new Payment();
        payment1.setPaymentMethod(payment.getPaymentMethod());
        payment1.setStatus(payment.getStatus());
        return paymentRepository.save(payment1);
    }

    @Override
    public Boolean updatePayment(Integer paymentId, ReqCreatePayment payment) {
        Optional<Payment> payments = paymentRepository.findById(paymentId);
        if (payments.isPresent()) {
            Payment payment1 = payments.get();
            payment1.setPaymentMethod(payment.getPaymentMethod());
            payment1.setStatus(payment.getStatus());
            paymentRepository.saveAndFlush(payment1);
            return true;
        } else {
            return false;
        }
    }

    public Boolean deletePayment(Integer paymentId) {
        Optional<Payment> payments = paymentRepository.findById(paymentId);
        if (payments.isPresent()) {
            Payment payment1= payments.get();
            paymentRepository.delete(payment1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Schedule> findAllSchedule() {
        return scheduleRepository.findAll();
    }



    @Override
    public Schedule saveSchedule(ReqCreateSchedule schedule) {
        Schedule schedule1 = new Schedule();
        schedule1.setDestinationId(schedule.getDestinationId());
        schedule1.setClassId(schedule.getClassId());
        schedule1.setPrice(schedule.getPrice());
        schedule1.setAirplanesId(schedule.getAirplanesId());
        schedule1.setFlight(schedule.getFlight());
        return scheduleRepository.save(schedule1);
    }

    @Override
    public Boolean updateSchedule(Integer scheduleId,ReqCreateSchedule schedule) {
        Optional<Schedule> schedules = scheduleRepository.findById(scheduleId);
        if (schedules.isPresent()) {
            Schedule schedule1 = schedules.get();
            schedule1.setDestinationId(schedule.getDestinationId());
            schedule1.setClassId(schedule.getClassId());
            schedule1.setPrice(schedule.getPrice());
            schedule1.setAirplanesId(schedule.getAirplanesId());
            schedule1.setFlight(schedule.getFlight());
            scheduleRepository.saveAndFlush(schedule1);
            return true;
        } else {
            return false;
        }
    }

    public Boolean deleteSchedule(Integer scheduleId) {
        Optional<Schedule> schedules = scheduleRepository.findById(scheduleId);
        if (schedules.isPresent()) {
            Schedule schedule1= schedules.get();
            scheduleRepository.delete(schedule1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Seats> findAllSeats() {
        return seatsRepository.findAll();
    }

    public Optional<Seats> findBySeatsId(Integer seatsId){
        return seatsRepository.findById(seatsId);
    }

    @Override
    public Seats saveSeats(ReqCreateSeats seats) {
        Seats seats1 = new Seats();
        seats1.setSeatsGroup(seats.getSeatsGroup());
        seats1.setSeatsNumber(seats.getSeatsNumber());
        seats1.setPositions(seats.getPositions());
        seats1.setClassId(seats.getClassId());
        seats1.setAirplanesId(seats.getAirplanesId());
        seats1.setClassSeats(seats.getClassSeats());
        return seatsRepository.save(seats1);
    }

    @Override
    public Boolean updateSeats(Integer seatsId, ReqCreateSeats seats) {
        Optional<Seats> seat = seatsRepository.findById(seatsId);
        if (seat.isPresent()) {
            Seats seat1 = seat.get();
            seat1.setSeatsGroup(seats.getSeatsGroup());
            seat1.setSeatsNumber(seats.getSeatsNumber());
            seat1.setPositions(seats.getPositions());
            seat1.setClassId(seats.getClassId());
            seat1.setAirplanesId(seats.getAirplanesId());
            seat1.setClassSeats(seats.getClassSeats());
            seatsRepository.saveAndFlush(seat1);
            return true;
        } else {
            return false;
        }
    }

    public Boolean deleteSeats(Integer seatsId) {
        Optional<Seats> seats = seatsRepository.findById(seatsId);
        if (seats.isPresent()) {
            Seats seats1= seats.get();
            seatsRepository.delete(seats1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateAddClass(ReqUpdateClass req) {
        if (req.getId() > 0) {
            Optional<ClassSeats> classSeats = classRepository.findById(req.getId());
            if (classSeats.isPresent()) {
                ClassSeats c = classSeats.get();
                c.setName(req.getName());
                c.setPrice(req.getPrice());
                classRepository.saveAndFlush(c);
                return true;
            } else {
                return false;
            }
        } else {
            ClassSeats c = new ClassSeats();
            c.setName(req.getName());
            c.setPrice(req.getPrice());
            classRepository.saveAndFlush(c);
            return true;
        }
    }

    @Override
    public Boolean deleteClassSeats(int id) {
        Optional<ClassSeats> classSeats = classRepository.findById(id);
        if (classSeats.isPresent()) {
            ClassSeats c = classSeats.get();
            classRepository.delete(c);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateAddDestination(ReqUpdateDestination req) {
        if (req.getId() > 0) {
            Optional<Destination> destination = destinationRepository.findById(req.getId());
            if (destination.isPresent()) {
                Destination d = destination.get();
                d.setDestinations(req.getDestinationCityId());
                d.setDeparture(req.getDepartureCityId());
                d.setDuration(req.getDuration());
                d.setAdditionalPrice(req.getPrice());
                destinationRepository.saveAndFlush(d);
                return true;
            } else {
                return false;
            }
        } else {
            Destination d = new Destination();
            d.setDestinations(req.getDestinationCityId());
            d.setDeparture(req.getDepartureCityId());
            d.setDuration(req.getDuration());
            d.setAdditionalPrice(req.getPrice());
            destinationRepository.saveAndFlush(d);
            return true;
        }
    }

    @Override
    public Boolean deleteDestination(int id) {
        Optional<Destination> destination = destinationRepository.findById(id);
        if (destination.isPresent()) {
            Destination d = destination.get();
            destinationRepository.delete(d);
            return true;
        } else {
            return false;
        }
    }
}
