package com.takehome.stayease.exchange;

import com.takehome.stayease.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingRequest {


    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    // private Long hotelID;

    // private Long bookingID;

    private BookingStatus status;
}
