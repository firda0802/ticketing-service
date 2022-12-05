package com.binar.tix.service;

import com.binar.tix.entities.Airport;
import com.binar.tix.payload.ReqCreateAirport;
import com.binar.tix.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportServiceImpl implements AirportService {
    @Autowired
    private AirportRepository airportRepository;

    @Override
    public List<Airport> getAirportIsShowing() {
        return null;
    }

    @Override
    public void createAirport(ReqCreateAirport airports) {
        Airport airport = new Airport();
        airport.setName(airports.getName());
        airport.setAddress(airports.getAddress());
    }

    @Override
    public Boolean updateAirport(Integer id, String name, String address) {
        Optional<Airport> airports = AirportRepository.findByAirportId(id);
        if (airports.isPresent()) {
            Airport airport = airports.get();
            airport.setName(name);
            airport.setAddress(address);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public Boolean deleteAirport(Integer id){
         int status = AirportRepository.deleteAirport(id);
         return status > 0;
    }

}
