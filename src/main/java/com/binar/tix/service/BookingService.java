/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.binar.tix.service;

import com.binar.tix.entities.*;
import com.binar.tix.payload.*;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
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

    String createOrder(ReqCreateOrder req, int userId, RespScheduleReturn scheduleReturn);

    String getTitle(String title);

    RespScheduleReturn getScheduleReturn(int scheduleId, LocalDate returnDate);

    Page<Orders> historyBooking(int userId, Pageable paging);

    Messages detailHistory(String invoiceNo);
}
