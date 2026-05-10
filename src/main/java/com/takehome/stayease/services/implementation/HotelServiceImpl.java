package com.takehome.stayease.services.implementation;

import com.takehome.stayease.entity.Hotel;
import com.takehome.stayease.entity.Location;
import com.takehome.stayease.exchange.HotelRequest;
import com.takehome.stayease.repository.HotelRepository;
import com.takehome.stayease.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;

    public Hotel create(HotelRequest hotelRequest) {
        // String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Location location = new Location();
        location.setAddress(hotelRequest.getLocation());
        location.setLatitude(hotelRequest.getLatitude());
        location.setLongitude(hotelRequest.getLongitude());

        Hotel hotel = Hotel.builder()
                .name(hotelRequest.getName())
                .description(hotelRequest.getDescription())
                .availableRooms(hotelRequest.getAvailableRooms().orElse(0))
                .location(location)
                .build();

        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel update(Long id, Map<String, Object> hotelRequest) {

        Hotel existingHotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));

        if (hotelRequest.get("name") != null) {
            existingHotel.setName((String) hotelRequest.get("name"));
        }
        if (hotelRequest.get("description") != null) {
            existingHotel.setDescription((String) hotelRequest.get("description"));
        }
        if (hotelRequest.get("availableRooms") != null) {
            existingHotel.setAvailableRooms((int) hotelRequest.get("availableRooms"));
        }

        Location existingLocation = existingHotel.getLocation();
        if (existingLocation == null) {
            existingLocation = new Location();
            existingHotel.setLocation(existingLocation);
        }
        if (hotelRequest.get("address") != null) {
            existingLocation.setAddress((String) hotelRequest.get("address"));
        }
        if (hotelRequest.get("latitude") != null) {
            existingLocation.setLatitude((Double) hotelRequest.get("latitude"));
        }
        if (hotelRequest.get("longitude") != null) {
            existingLocation.setLongitude((Double) hotelRequest.get("longitude"));
        }

            hotelRepository.save(existingHotel);
        return existingHotel;
    }

    @Override
    public List<Hotel> get() {
        return hotelRepository.findAll();
    }
    @Override
    public Optional<Hotel> get(Long id) {
        Optional<Hotel> o = hotelRepository.findById(id);
        if (o.isPresent()) {
            return o;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
        }
    }

    @Override
    public boolean delete(Long id) {
        Optional<Hotel> o = hotelRepository.findById(id);
        if (o.isPresent()) {
            hotelRepository.deleteById(id);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
        }
    }
}
