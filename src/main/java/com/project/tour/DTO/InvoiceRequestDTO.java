package com.project.tour.DTO;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// ===========================
// Request DTO
// ===========================
@Data
public class InvoiceRequestDTO {

    @NotBlank(message = "Trạng thái không được để trống")
    private String status;

    @NotBlank(message = "Phương thức thanh toán không được để trống")
    private String paymentMethod;

    @NotBlank(message = "Cần chỉ định tour")
    private String tourId;

    @NotBlank(message = "Cần liên kết với một tài khoản")
    private String accountId;

    private List<String> promotionIds; // Danh sách mã khuyến mãi
}
