package com.project.tour.DTO;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceResponseDTO {

    private Long invoiceId;
    private String invoiceCreatedAt;
    private String status;

    // --- Thông tin tóm lược từ Tour ---
    private String tourId;
    private String tourName;

    // --- Thông tin tóm lược từ Account ---
    private Long accountId;
    private String username;

    // --- Thông tin tóm lược từ Promotion ---
    // Trả về danh sách tên các khuyến mãi đã áp dụng
    private Set<String> appliedPromotions;
}