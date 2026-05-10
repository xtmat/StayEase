package com.takehome.stayease.exchange;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.takehome.stayease.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelRequest {

    @NotEmpty(message = "Hotel name cannot be empty")
    @NotBlank(message = "Hotel name cannot be blank")
    private String name;

    @NotEmpty(message = "Description cannot be empty")
    @NotBlank(message = "Description cannot be blank")
    private String description;

    private Optional<Integer> availableRooms;

    @NotNull(message = "Address cannot be null")
    private String location;

    private Double latitude;
    
    private Double longitude;

    public Optional<Hotel> getNoOfRooms() {
        return null;
    }
}
