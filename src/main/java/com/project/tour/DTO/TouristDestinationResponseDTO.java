package com.project.tour.DTO;

import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TouristDestinationResponseDTO {

    private Long destinationId;
    private String destinationName;
    private String location;
    private String description;
    private Double entryFee;

    // --- Thông tin tóm lược từ Tour ---
    // Trả về một Set chứa ID và tên của các tour có điểm đến này
    private Set<Map<String, String>> tours;
}