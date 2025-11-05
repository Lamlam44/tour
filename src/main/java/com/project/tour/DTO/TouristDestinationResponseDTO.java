package com.project.tour.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TouristDestinationResponseDTO {

    private String destinationId;
    private String destinationName;
    private String location;
    private String description;
    private Double entryFee;
}