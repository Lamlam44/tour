package com.project.tour.Entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.*;
import java.util.Set;;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany(mappedBy = "tours", fetch = FetchType.LAZY)
    private Set<Accommodation> accommodations;

    @ManyToMany(mappedBy = "tours", fetch = FetchType.LAZY)
    private Set<Promotion> promotions;

    @ManyToMany(mappedBy = "tours", fetch = FetchType.LAZY)
    private Set<TravelVehicle> travelVehicles;

    @ManyToMany(mappedBy = "tours", fetch = FetchType.LAZY)
    private Set<TouristDestination> touristDestinations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_guide_id")
    private TourGuide tourGuide;
}
