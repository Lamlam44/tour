package com.project.tour.DTO;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceResponseDTO {

    private String invoiceId;
    private String invoiceCreatedAt;
    private String status;
    private Double discountAmount;
    private Double taxAmount;
    private Double totalAmount;
    private String paymentMethod;

    // --- Thông tin tóm lược từ Tour ---
    private TourResponseDTO tour;

    // --- Thông tin tóm lược từ Account ---
    private AccountResponseDTO account;

    // --- Thông tin tóm lược từ Promotion ---
    private Set<PromotionResponseDTO> appliedPromotions;
}
