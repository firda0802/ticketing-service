package com.binar.tix.service;

import com.binar.tix.entities.Seats;
import com.binar.tix.payload.ReqCreateSeats;
import com.binar.tix.repository.SeatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatsServiceImpl implements SeatsService {

    @Autowired
    private SeatsRepository seatsRepository;

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
}
