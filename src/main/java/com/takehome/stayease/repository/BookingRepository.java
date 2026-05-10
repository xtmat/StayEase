package com.takehome.stayease.repository;

import com.takehome.stayease.entity.Booking;
import com.takehome.stayease.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByStatus(BookingStatus status);
}
