package com.project.tour.DTO;

import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionResponseDTO {

    private Long promotionId;
    private String promotionName;
    private Double discountPercentage;
    private String startDate;
    private String endDate;

    // --- Thông tin tóm lược từ Tour ---
    // Trả về một Set chứa ID và tên của các tour được áp dụng
    private Set<Map<String, String>> appliedTours;

    // Lưu ý: Chúng ta không trả về danh sách Invoices ở đây
    // để tránh dữ liệu lớn và lỗi vòng lặp. Nếu cần, hãy
    // tạo một API riêng. Ví dụ: GET /api/promotions/{id}/invoices
}