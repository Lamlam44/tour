package com.project.tour.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourGuideResponseDTO {

    private String tourGuideId;
    private String tourGuideName;
    private String tourGuideEmail;
    private String tourGuidePhone;
    private int tourGuideExperienceYears;

}