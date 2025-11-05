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
@Table(name = "tourist_destinations")
public class TouristDestination {
    @Id
    @Column(length = 10)
    private String destinationId;

    @Column(name = "destination_name", nullable = false)
    private String destinationName;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "entry_fee")
    private Double entryFee;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "touristDestinations")
    private Set<Tour> tours;
}
