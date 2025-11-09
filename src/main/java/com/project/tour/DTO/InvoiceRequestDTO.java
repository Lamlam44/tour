package com.project.tour.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceRequestDTO {

    @NotBlank(message = "Trạng thái không được để trống")
    private String status;

    @NotBlank(message = "Số tiền giảm giá không được để trống")
    private Double discountAmount;

    @NotBlank(message = "Số tiền thuế không được để trống")
    private Double taxAmount;

    @NotBlank(message = "Tổng số tiền không được để trống")
    private Double totalAmount;

    @NotBlank(message = "Phương thức thanh toán không được để trống")
    private String paymentMethod;

    @NotNull(message = "Cần chỉ định tour")
    private String tourId;

    @NotNull(message = "Cần liên kết với một tài khoản")
    private String accountId; 

    // Client chỉ cần gửi danh sách ID của các khuyến mãi
    private Set<String> promotionIds; 
}