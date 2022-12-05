package com.binar.tix.service;


import com.binar.tix.entities.Airport;
import com.binar.tix.payload.ReqCreateAirport;

import java.util.List;

public interface AirportService {
    List<Airport> getAirportIsShowing();
    void createAirport(ReqCreateAirport airport);
    Boolean updateAirport(Integer id, String name, String address);
    Boolean deleteAirport(Integer id);

}
