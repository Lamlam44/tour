package com.project.tour.DTO;

import java.util.Set;
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

    // Trả về một danh sách tóm lược các tour có liên quan
    private Set<TourResponseDTO> tours;
}