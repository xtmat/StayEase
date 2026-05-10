package com.takehome.stayease.entity;

import com.takehome.stayease.model.BookingStatus;
import com.takehome.stayease.security.entity.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Entity
@Table(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Booking {

    @Id
    @GeneratedValue
    @JsonProperty("bookingId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    @JsonBackReference
    private Users user;


    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonBackReference
    private Hotel hotel;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    public Booking(Users user, Hotel hotel, BookingStatus bookingStatus) {
        this.user = user;
        this.hotel = hotel;
        this.status = bookingStatus;
    }
}
