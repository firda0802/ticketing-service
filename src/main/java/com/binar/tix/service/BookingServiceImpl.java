package com.binar.tix.service;

import com.binar.tix.entities.*;
import com.binar.tix.payload.Messages;
import com.binar.tix.payload.ReqShowBooking;
import com.binar.tix.payload.RespScheduleData;
import com.binar.tix.repository.*;
import com.binar.tix.utility.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {


    @Autowired
    AirportRepository airportRepository;
    @Autowired
    DestinationRepository destinationRepository;
    @Autowired
    DestinationCityRepository destinationCityRepository;

    @Autowired
    ClassRepository classRepository;

    @Autowired
    PassengerTypeRepository passengerTypeRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Override
    public List<DestinationCity> listDestinationCity() {
        return destinationCityRepository.findAll();
    }

    @Override
    public List<ClassSeats> listClassSeat() {
        return classRepository.findAll();
    }

    @Override
    public List<PassengerType> listPassengerType() {
        return passengerTypeRepository.findAll();
    }

    @Override
    public List<Schedule> getSchedule() {
        return scheduleRepository.findAll();
    }

    @Override
    public Messages showScheduleFlight(ReqShowBooking req) {
        Messages msg = new Messages();
        msg.setResponseCode(Constant.NO_CONTENT);
        msg.setResponseMessage("Tidak ada jadwal penerbangan");
        Optional<Destination> cekDestination = destinationRepository.findByDepartureAndDestinations(req.getDepartureCity(), req.getDestinationsCity());
        if (cekDestination.isPresent()) {
            Destination destination = cekDestination.get();
            List<Schedule> schedules = scheduleRepository.findByDestinationIdAndClassIdAndFlightDate(destination.getDestinationId(), req.getClassSeats(), req.getDate());
            if (!schedules.isEmpty()) {
                List<RespScheduleData> list = new ArrayList<>();
                for (Schedule s : schedules) {
                    RespScheduleData data = new RespScheduleData();
                    data.setScheduleId(s.getScheduleId());
                    data.setClassType(s.getClassSeats().getName());
                    data.setLuggageCapacity(s.getAirplane().getLuggageCapacity()+"KG");
                    data.setDepartureCity(destination.getDepartureCity().getCityName());
                    data.setDestinationsCity(destination.getDestinationsCity().getCityName());
                    data.setAirplane(s.getAirplane().getType());
                    data.setAirport(s.getAirplane().getAirport().getName());
                    data.setAirportAddress(s.getAirplane().getAirport().getAddress());
                    data.setStartTime(s.getStartTime());
                    data.setEndTime(s.getEndTime());
                    data.setPrice(s.getPricing().getPrice() + destination.getAdditionalPrice());
                    list.add(data);
                }
                msg.success();
                msg.setData(list);
            }
        }

        return msg;
    }
}
