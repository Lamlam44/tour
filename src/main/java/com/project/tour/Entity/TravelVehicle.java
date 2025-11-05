package com.project.tour.Entity;

import java.util.Set;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "travel_vehicles")
public class TravelVehicle {
    @Id
    @Column(length = 10)
    private String vehicleId;

    @Column(name = "vehicle_type", nullable = false)
    private String vehicleType;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "rental_price_per_day", nullable = false)
    private Double rentalPricePerDay;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "travelVehicles")
    private Set<Tour> tours;
}
