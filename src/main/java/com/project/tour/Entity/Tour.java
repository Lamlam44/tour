package com.project.tour.Entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.*;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tours")
public class Tour {
    @Id
    @Column(length = 10)
    private String tourId;

    @Column(name = "tour_name", nullable = false)
    private String tourName;

    @Column(name = "tour_description", nullable = false, length = 1000)
    private String tourDescription;

    @Column(name = "tour_price", nullable = false)
    private double tourPrice;

    @Column(name = "tour_status", nullable = false)
    private String tourStatus;

    @Column(name = "tour_image", nullable = false)
    private String tourImage;

    @Column(name = "tour_start_date", nullable = false)
    private LocalDateTime tourStartDate;

    @Column(name = "tour_end_date", nullable = false)
    private LocalDateTime tourEndDate;

    @Column(name = "tour_remaining_slots", nullable = false)
    private int tourRemainingSlots;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tour")
    private Set<Invoice> invoices;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @ManyToMany(mappedBy = "tours", fetch = FetchType.LAZY)
    private Set<Promotion> promotions;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "tour_vehicles",
        joinColumns = @JoinColumn(name = "tour_id"),
        inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    private Set<TravelVehicle> travelVehicles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "tour_destinations",
        joinColumns = @JoinColumn(name = "tour_id"),
        inverseJoinColumns = @JoinColumn(name = "destination_id")
    )
    private Set<TouristDestination> touristDestinations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_guide_id")
    private TourGuide tourGuide;
}
