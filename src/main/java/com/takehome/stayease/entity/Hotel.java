package com.takehome.stayease.entity;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "hotels")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Hotel {

    @Id
    @GeneratedValue
    private Long id;


    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private String description;


    @JsonProperty("availableRooms")
    @Column(nullable = false)
    private int availableRooms;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;
}
