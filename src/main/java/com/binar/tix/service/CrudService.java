package com.binar.tix.service;

import com.binar.tix.entities.Airplane;
import com.binar.tix.entities.Payment;
import com.binar.tix.entities.Schedule;
import com.binar.tix.entities.Seats;
import com.binar.tix.payload.*;

import java.util.List;

public interface CrudService {

    List<Airplane> findAllAirplanes();

    Airplane saveAirplane(ReqCreateAirplane airplane);

    Boolean updateAirplane(Integer airplaneId, ReqCreateAirplane airplane);

    Boolean deleteAirplane(Integer airplaneId);

    Boolean updateAirport(Integer id, String name, String address, int cityId);

    Boolean deleteAirport(Integer id);

    List<Payment> findAllPayment();

    Payment savePayment(ReqCreatePayment payment);

    Boolean updatePayment(Integer paymentId, ReqCreatePayment payment);

    Boolean deletePayment(Integer paymentId);

    List<Schedule> findAllSchedule(int limit, int offset);

    Schedule saveSchedule(ReqCreateSchedule schedule);

    Boolean updateSchedule(Integer scheduleId, ReqCreateSchedule schedule);

    Boolean deleteSchedule(Integer scheduleId);

    List<Seats> findAllSeats();

    Seats saveSeats(ReqCreateSeats seats);

    Boolean updateSeats(Integer seatsId, ReqCreateSeats seats);

    Boolean deleteSeats(Integer seatsId);

    Boolean updateAddClass(ReqUpdateClass req);

    Boolean deleteClassSeats(int id);

    Boolean updateAddDestination(ReqUpdateDestination req);

    Boolean deleteDestination(int id);

}
