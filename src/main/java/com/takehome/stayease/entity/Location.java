package com.takehome.stayease.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "locations")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Location {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @OneToOne(mappedBy = "location")
    @JsonIgnore
    private Hotel hotel;
}
