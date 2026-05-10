package com.takehome.stayease.services.implementation;

import com.takehome.stayease.entity.Booking;
import com.takehome.stayease.entity.Hotel;
import com.takehome.stayease.exchange.BookingRequest;
import com.takehome.stayease.model.BookingStatus;
import com.takehome.stayease.repository.BookingRepository;
import com.takehome.stayease.repository.HotelRepository;
import com.takehome.stayease.security.entity.Users;
import com.takehome.stayease.security.repository.UserRepository;
import com.takehome.stayease.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;

    @Override
    public List<Hotel> availableRooms(Optional<Integer> room) {
        return hotelRepository.findHotelsByAvailableRoomsGreaterThanEqual(room.orElse(1));
    }

    @Override
    public Booking book(Long hotelId, BookingRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); 
        System.out.print("Username for the booking customer : "+ username);
        if (hotelId!= null) {
            Optional<Hotel> oh = hotelRepository.findById(hotelId);
            if (oh.isPresent()) {
                Hotel hotel = oh.get();
                if (hotel.getAvailableRooms() >= 1) {
                    Users user = userRepository.findByEmail(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email id : "+username+" not found"));
                    hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
                    hotelRepository.save(hotel);
                    Booking booking = new Booking(user, hotel, BookingStatus.BOOKED);
                    booking.setCheckInDate(request.getCheckInDate());
                    booking.setCheckOutDate(request.getCheckOutDate());
                    return bookingRepository.save(booking);
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No rooms available");
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
    }


    @Override
    public Booking update(Long bookingId, BookingRequest request) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()->new RuntimeException("No booking available with given id"));
        if(booking.getStatus() != null) {
            if (booking.getStatus().equals(BookingStatus.CANCELLED))
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Booking can't be cancelled");

                booking.setStatus(request.getStatus());
                return bookingRepository.save(booking);
        }
        return booking;
    }


    @Override
    public Booking cancel(Long id) {
        Optional<Booking> b = bookingRepository.findById(id);
        if (b.isPresent()) {
            Booking booking = b.get();
            booking.setStatus(BookingStatus.CANCELLED);
            Optional<Hotel> oh = hotelRepository.findById(booking.getHotel().getId());
            if (oh.isPresent()) {
                Hotel hotel = oh.get();
                hotel.setAvailableRooms(hotel.getAvailableRooms() + 1);
                hotelRepository.save(hotel);
            }
            return bookingRepository.save(booking);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
    }


    @Override
    public List<Booking> get() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> getByStatus(BookingStatus status) {
        return bookingRepository.findAllByStatus(status);
    }

    @Override
    public Optional<Booking> getById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public boolean delete(Long id) {
        if (bookingRepository.findById(id).isPresent()) {
            bookingRepository.deleteById(id);
            return true;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
    }
}
