package com.project.tour.DTO;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourRequestDTO {

    @NotBlank(message = "Tên tour không được để trống")
    @Size(max = 255, message = "Tên tour không được vượt quá 255 ký tự")
    private String tourName;

    @NotBlank(message = "Mô tả tour không được để trống")
    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    private String tourDescription;

    @NotNull(message = "Giá tour không được để trống")
    @Positive(message = "Giá tour phải là số dương")
    private double tourPrice;

    @NotBlank(message = "Trạng thái tour không được để trống")
    private String tourStatus;

    @NotBlank(message = "Ảnh tour không được để trống")
    private String tourImage;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    @FutureOrPresent(message = "Ngày bắt đầu phải là ngày hiện tại hoặc trong tương lai")
    private LocalDateTime tourStartDate;

    @NotNull(message = "Ngày kết thúc không được để trống")
    @Future(message = "Ngày kết thúc phải ở trong tương lai")
    private LocalDateTime tourEndDate;

    @NotNull(message = "Số chỗ còn lại không được để trống")
    @Min(value = 0, message = "Số chỗ không thể là số âm")
    private int tourRemainingSlots;

    // ----- Dữ liệu cho các mối quan hệ -----
    // Khi tạo tour, client chỉ cần gửi ID của các thực thể liên quan
    
    @NotNull(message = "Cần chỉ định hướng dẫn viên")
    private String tourGuideId;

    private Set<String> accommodationIds;
    private Set<String> promotionIds;
    private Set<String> travelVehicleIds;
    private Set<String> touristDestinationIds;
}