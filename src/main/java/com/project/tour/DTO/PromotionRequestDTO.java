package com.project.tour.DTO;

import jakarta.validation.constraints.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionRequestDTO {

    @NotBlank(message = "Tên khuyến mãi không được để trống")
    private String promotionName;

    @NotNull(message = "Phần trăm giảm giá không được để trống")
    @Positive(message = "Phần trăm giảm giá phải là số dương")
    private Double discountPercentage;

    @NotBlank(message = "Ngày bắt đầu không được để trống")
    private String startDate;

    @NotBlank(message = "Ngày kết thúc không được để trống")
    private String endDate;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    // Client chỉ cần gửi danh sách ID của các tour áp dụng khuyến mãi
    private Set<String> tourIds; // Giữ nguyên kiểu String cho tourId
}