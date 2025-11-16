package com.project.tour.DTO;

import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionResponseDTO {

    private String promotionId;
    private String promotionName;
    private Double discountPercentage;
    private String startDate;
    private String endDate;
    private String description;
    // --- Thông tin tóm lược từ Tour ---
    // Trả về một Set chứa ID và tên của các tour được áp dụng
    private Set<Map<String, String>> appliedTours;

}