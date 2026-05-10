package com.takehome.stayease.controller;

import com.takehome.stayease.exchange.HotelRequest;
import com.takehome.stayease.services.HotelService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("")
    public ResponseEntity<?> getAllHotels() {
        return ResponseEntity.ok(hotelService.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelById(@PathVariable long id) {
        return ResponseEntity.ok(hotelService.get(id));
    }

    @PostMapping("")
    public ResponseEntity<?> saveHotel(@Valid @RequestBody HotelRequest hotel) {
        return ResponseEntity.ok(hotelService.create(hotel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHotel(@PathVariable Long id, @RequestBody Map<String, Object> hotel) {
        return ResponseEntity.ok(hotelService.update(id, hotel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHotelById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(hotelService.delete(id));
    }
}
