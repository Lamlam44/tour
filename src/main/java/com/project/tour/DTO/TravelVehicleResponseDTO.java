package com.project.tour.DTO;

import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelVehicleResponseDTO {

    private Long vehicleId;
    private String vehicleType;
    private int capacity;
    private Double rentalPricePerDay;

    // --- Thông tin tóm lược từ Tour ---
    // Trả về một Set chứa ID và tên của các tour sử dụng phương tiện này
    private Set<Map<String, String>> tours;
}