package com.project.tour.DTO;

import com.project.tour.common.PaymentMethod;
import com.project.tour.common.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceRequestDTO {

    // Status is set by the backend, should not be part of the request
    private PaymentStatus status;

    @NotNull(message = "Số tiền giảm giá không được để trống")
    private Double discountAmount;

    @NotNull(message = "Số tiền thuế không được để trống")
    private Double taxAmount;

    @NotNull(message = "Tổng số tiền không được để trống")
    private Double totalAmount;

    @NotNull(message = "Cần chỉ định số người tham gia")
    private Integer numberOfPeople;

    // Payment method is set during payment, not creation
    private PaymentMethod paymentMethod;

    private String customerName;
    private String customerPhone;
    private String customerEmail;

    @NotNull(message = "Cần chỉ định tour")
    private String tourId;

    private String accountId; 

    // Client chỉ cần gửi danh sách ID của các khuyến mãi
    private Set<String> promotionIds; 
}