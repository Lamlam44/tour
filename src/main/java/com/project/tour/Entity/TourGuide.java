package com.project.tour.Entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tour_guides")
public class TourGuide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String tourGuideId;

    @Column(name = "tour_guide_name", nullable = false)
    private String tourGuideName;

    @Column(name = "tour_guide_email", nullable = false)
    private String tourGuideEmail;

    @Column(name = "tour_guide_phone", nullable = false)
    private String tourGuidePhone;

    @Column(name = "tour_guide_experience_years", nullable = false)
    private int tourGuideExperienceYears;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "tourGuide")
    private Tour tour;
}
