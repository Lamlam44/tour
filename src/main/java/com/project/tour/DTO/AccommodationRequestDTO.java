package com.project.tour.DTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccommodationRequestDTO {

    @NotBlank(message = "Tên nơi ở không được để trống")
    private String accommodationName;

    @NotBlank(message = "Vị trí không được để trống")
    private String location;

    @PositiveOrZero(message = "Đánh giá không thể là số âm")
    private Double rating;

    @NotNull(message = "Giá mỗi đêm không được để trống")
    @Positive(message = "Giá mỗi đêm phải là số dương")
    private Double pricePerNight;

    @NotBlank(message = "Loại hình nơi ở không được để trống")
    private String accommodationType;
    
}