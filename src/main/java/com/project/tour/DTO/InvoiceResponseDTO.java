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

    // --- Thông tin tóm lược từ Tour ---
    private TourResponseDTO tour;

    // --- Thông tin tóm lược từ Account ---
    private AccountResponseDTO account;

    // --- Thông tin tóm lược từ Promotion ---
    // Trả về danh sách tên các khuyến mãi đã áp dụng
    private Set<PromotionResponseDTO> appliedPromotions;
}