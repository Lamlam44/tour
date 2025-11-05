package com.project.tour.DTO;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourResponseDTO {

    private String tourId;
    private String tourName;
    private String tourDescription;
    private double tourPrice;
    private String tourStatus;
    private String tourImage;
    private LocalDateTime tourStartDate;
    private LocalDateTime tourEndDate;
    private int tourRemainingSlots;

    // ----- Dữ liệu của các mối quan hệ -----
    // Trả về các đối tượng DTO tóm lược, không phải Entity đầy đủ

    private TourGuideResponseDTO tourGuide;
    private AccommodationResponseDTO accommodation;
    private Set<TravelVehicleResponseDTO> travelVehicles;
    private Set<TouristDestinationResponseDTO> touristDestinations;
    
}