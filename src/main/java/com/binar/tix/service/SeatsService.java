package com.binar.tix.service;

import com.binar.tix.entities.Seats;
import com.binar.tix.payload.ReqCreateSeats;

import java.util.List;
import java.util.Optional;

public interface SeatsService {

    List<Seats> findAllSeats();

    Optional<Seats> findBySeatsId(Integer seatsId);

    Seats saveSeats(ReqCreateSeats seats);

    Boolean updateSeats(Integer seatsId, ReqCreateSeats seats);

    Boolean deleteSeats(Integer seatsId);
}
