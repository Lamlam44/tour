package com.project.tour.DTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelVehicleRequestDTO {

    @NotBlank(message = "Loại phương tiện không được để trống")
    private String vehicleType;

    @NotNull(message = "Sức chứa không được để trống")
    @Positive(message = "Sức chứa phải là số dương")
    private int capacity;

    @NotNull(message = "Giá thuê mỗi ngày không được để trống")
    @Positive(message = "Giá thuê phải là số dương")
    private Double rentalPricePerDay;
}