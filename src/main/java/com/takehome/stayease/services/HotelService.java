package com.takehome.stayease.services;

import com.takehome.stayease.entity.Hotel;
import com.takehome.stayease.exchange.HotelRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface HotelService {


    Hotel create(HotelRequest hotel);

    Hotel update(Long id, Map<String, Object> hotel);


    List<Hotel> get();


    Optional<Hotel> get(Long id);

    boolean delete(Long id);
}
