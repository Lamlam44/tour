package com.project.tour.DTO;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponseDTO {

    private String accountId;
    private String username;
    private LocalDateTime accountCreatedAt;

    // Trả về thông tin vai trò dưới dạng một DTO khác để dễ quản lý
    private AccountRoleResponseDTO role;

    // Chúng ta thường không trả về Customer và Invoices trong DTO này
    // để tránh dữ liệu quá lớn. Thay vào đó, hãy tạo các API riêng
    // để lấy thông tin Customer hoặc Invoices của một Account.
    // Ví dụ: GET /api/accounts/{accountId}/customer
}