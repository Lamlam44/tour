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
@Table(name = "accommodations")
public class Accommodation {
    @Id
    @Column(length = 10)
    private String accommodationId;

    @Column(name = "accommodation_name", nullable = false)
    private String accommodationName;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "price_per_night")
    private Double pricePerNight;

    @Column(name = "accommodation_type", nullable = false)
    private String accommodationType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accommodation")
    private Set<Tour> tours;
}
