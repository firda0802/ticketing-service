package com.binar.tix.service;

import com.binar.tix.entities.Airplane;
import com.binar.tix.payload.ReqCreateAirplane;
import com.binar.tix.repository.AirplaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirplaneServiceImpl implements AirplaneService{

    @Autowired
    AirplaneRepository airplaneRepository;

    @Override
    public List<Airplane> findAllAirplanes() {
        return airplaneRepository.findAll();
    }

    @Override
    public Optional<Airplane> findByAirplaneId(Integer airplaneId){
        return airplaneRepository.findById(airplaneId);
    }

    @Override
    public Airplane saveAirplane(ReqCreateAirplane req) {
        Airplane airplane1 = new Airplane();
        airplane1.setType(req.getType());
        airplane1.setLuggageCapacity(req.getLuggageCapacity());
        airplane1.setAirportId(req.getAirportId());
        return airplaneRepository.save(airplane1);
    }

    @Override
    public Boolean updateAirplane(Integer airplaneId, ReqCreateAirplane airplane) {
        Optional<Airplane> airplanes = airplaneRepository.findById(airplaneId);
        if (airplanes.isPresent()) {
            Airplane airplane1 = airplanes.get();
            airplane1.setType(airplane.getType());
            airplane1.setLuggageCapacity(airplane.getLuggageCapacity());
            airplane1.setAirportId(airplane.getAirportId());
            airplaneRepository.saveAndFlush(airplane1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteAirplane(Integer airplaneId) {
        Optional<Airplane> airplanes = airplaneRepository.findById(airplaneId);
        if (airplanes.isPresent()) {
            Airplane airplane1= airplanes.get();
            airplaneRepository.delete(airplane1);
            return true;
        } else {
            return false;
        }
    }
}
