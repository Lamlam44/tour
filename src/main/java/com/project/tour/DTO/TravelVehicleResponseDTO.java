package com.project.tour.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelVehicleResponseDTO {

    private String vehicleId;
    private String vehicleType;
    private int capacity;
    private Double rentalPricePerDay;
}