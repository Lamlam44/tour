package com.project.tour.DTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourGuideRequestDTO {

    @NotBlank(message = "Tên hướng dẫn viên không được để trống")
    private String tourGuideName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String tourGuideEmail;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String tourGuidePhone;

    @NotNull(message = "Số năm kinh nghiệm không được để trống")
    @Min(value = 0, message = "Số năm kinh nghiệm không thể là số âm")
    private int tourGuideExperienceYears;
}