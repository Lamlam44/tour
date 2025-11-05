package com.project.tour.DTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TouristDestinationRequestDTO {

    @NotBlank(message = "Tên điểm đến không được để trống")
    private String destinationName;

    @NotBlank(message = "Vị trí không được để trống")
    private String location;

    @Size(max = 2000, message = "Mô tả không được vượt quá 2000 ký tự")
    private String description;

    @PositiveOrZero(message = "Phí vào cửa không thể là số âm")
    private Double entryFee;
    
}