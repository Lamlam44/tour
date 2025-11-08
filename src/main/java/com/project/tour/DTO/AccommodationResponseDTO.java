package com.project.tour.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccommodationResponseDTO {

    private String accommodationId;
    private String accommodationName;
    private String location;
    private Double rating;
    private Double pricePerNight;
    private String accommodationType;
}