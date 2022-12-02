package com.binar.tix.service;

import com.binar.tix.entities.*;
import com.binar.tix.enums.StatusEnum;
import com.binar.tix.payload.*;
import com.binar.tix.repository.*;
import com.binar.tix.utility.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
                    data.setLuggageCapacity(s.getAirplane().getLuggageCapacity() + "KG");
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

    @Override
    public Messages seatsAvailable(int scheduleId) {
        Messages msg = new Messages();
        List<DataSeats> resp = new ArrayList<>();
        Optional<Schedule> schedule = scheduleRepository.findById(scheduleId);
        if (schedule.isPresent()) {
            Schedule sch = schedule.get();
            for (Seats s : sch.getAirplane().getSeats()) {
                DataSeats r = new DataSeats();
                r.setSeatsNumber(s.getSeatsNumber());
                r.setSeatsGroup(s.getSeatsGroup() + "");
                r.setClassSeats(s.getClassId());
                r.setPosition(s.getPositions());
                r.setStatus(StatusEnum.AVAILABLE.name());
                resp.add(r);
            }
            msg.success();
            msg.setData(dataSeats(resp, sch.getClassId()));
        }else{
            msg.notFound();
        }
        return msg;
    }

    @Override
    public List<RespSeats> dataSeats(List<DataSeats> list, int classId) {
        List<RespSeats> resp = new ArrayList<>();
        Map<String, List<DataSeats>> myData = list.stream().collect(Collectors.groupingBy(DataSeats::getSeatsGroup));
        for (Map.Entry<String, List<DataSeats>> seats : myData.entrySet()) {
            String groupSeats = seats.getKey();
            seats.getValue().sort(Comparator.comparing(DataSeats::getPosition));
            RespSeats data = new RespSeats();
            data.setGroupSeats(groupSeats);
            List<RespSeatsDetail> detail = new ArrayList<>();
            for (DataSeats d : seats.getValue()) {
                RespSeatsDetail det = new RespSeatsDetail();
                det.setCanBook(d.getClassSeats() == classId);
                det.setSeatsNumber(d.getSeatsNumber());
                det.setStatusSeats(d.getStatus());
                detail.add(det);
            }
            data.setSeatsList(detail);
            resp.add(data);
        }
        return resp;
    }
}
