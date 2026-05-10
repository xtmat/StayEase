package com.takehome.stayease.controller;

import com.takehome.stayease.exchange.BookingRequest;
import com.takehome.stayease.model.BookingStatus;
import com.takehome.stayease.services.BookingService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/available-rooms")
    public ResponseEntity<?> getHotelHavingRequiredRooms(@RequestParam(required = false) Integer room) {
        return ResponseEntity.ok(bookingService.availableRooms(Optional.ofNullable(room)));
    }


    @PostMapping("/{hotelId}")
    public ResponseEntity<?> bookHotel(@PathVariable long hotelId, @Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.book(hotelId, request));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable long id, @RequestBody BookingRequest request) {

        return ResponseEntity.ok(bookingService.update(id, request));
    }


    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancel(id));
    }

    @GetMapping()
    public ResponseEntity<?> getAllBookings() {
        return ResponseEntity.ok(bookingService.get());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Long id) {
        return bookingService.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
    }


    @GetMapping("/status")
    public ResponseEntity<?> getBookingsByStatus(@RequestParam BookingStatus status) {
        return ResponseEntity.ok(bookingService.getByStatus(status));
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        if (bookingService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
        }
    }
}
