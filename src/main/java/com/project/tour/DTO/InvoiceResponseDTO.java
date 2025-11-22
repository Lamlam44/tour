package com.project.tour.DTO;

import com.project.tour.common.PaymentMethod;
import com.project.tour.common.PaymentStatus;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceResponseDTO {

    private String invoiceId;
    private String invoiceCreatedAt;
    private PaymentStatus status;
    private Integer numberOfPeople;
    private Double discountAmount;
    private Double taxAmount;
    private Double totalAmount;
    private PaymentMethod paymentMethod;
    private String customerName;
    private String customerPhone;
    private String customerEmail;

    // --- Thông tin tóm lược từ Tour ---
    private TourResponseDTO tour;

    // --- Thông tin tóm lược từ Account ---
    private AccountResponseDTO account;

    // --- Thông tin tóm lược từ Promotion ---
    // Trả về danh sách tên các khuyến mãi đã áp dụng
    private Set<PromotionResponseDTO> appliedPromotions;
}