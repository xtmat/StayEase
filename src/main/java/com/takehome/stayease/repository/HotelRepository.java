package com.takehome.stayease.repository;

import com.takehome.stayease.entity.Hotel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findHotelsByAvailableRoomsGreaterThanEqual(int roomAvailable);

}
