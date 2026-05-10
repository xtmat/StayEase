package com.takehome.stayease.services;

import com.takehome.stayease.entity.Booking;
import com.takehome.stayease.entity.Hotel;
import com.takehome.stayease.exchange.BookingRequest;
import com.takehome.stayease.model.BookingStatus;

import java.util.List;
import java.util.Optional;

public interface BookingService {

    List<Hotel> availableRooms(Optional<Integer> room);

    Booking book(Long hotelId, BookingRequest request);

    Booking update(Long bookingId,BookingRequest request);

    Booking cancel(Long request);

    List<Booking> get();

    Optional<Booking> getById(Long id);

    List<Booking> getByStatus(BookingStatus status);

    boolean delete(Long id);
}
