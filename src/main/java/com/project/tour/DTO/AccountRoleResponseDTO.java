package com.project.tour.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRoleResponseDTO {

    private String accountRoleId;
    private String roleName;
    
    // Lưu ý: Chúng ta không trả về Set<Account> ở đây để tránh
    // dữ liệu lớn và lỗi vòng lặp. Nếu cần danh sách tài khoản
    // theo vai trò, hãy tạo một API riêng.
    // Ví dụ: GET /api/roles/{roleId}/accounts
}