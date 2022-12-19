package com.binar.tix.service;

import com.binar.tix.entities.Airplane;
import com.binar.tix.payload.ReqCreateAirplane;

import java.util.List;
import java.util.Optional;

public interface AirplaneService {

    List<Airplane> findAllAirplanes();

    Optional<Airplane> findByAirplaneId(Integer airplaneId);

    Airplane saveAirplane(ReqCreateAirplane airplane);

    Boolean updateAirplane(Integer airplaneId, ReqCreateAirplane airplane);

    Boolean deleteAirplane(Integer airplaneId);


}
