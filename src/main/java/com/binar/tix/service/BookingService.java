/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.service;

import com.binar.tix.entities.*;
import com.binar.tix.payload.DataSeats;
import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqShowBooking;
import com.binar.tix.payload.RespSeats;

import java.util.List;

/**
 *
 * @author Riko
 */
public interface BookingService {

    List<DestinationCity> listDestinationCity();
    List<ClassSeats> listClassSeat();
    List<PassengerType> listPassengerType();

    List<Schedule> getSchedule();

    Messages showScheduleFlight(ReqShowBooking req);

    Messages seatsAvailable(int scheduleId);

    List<RespSeats> dataSeats(List<DataSeats> list, int classId);
}
