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
    private LocalDateTime accountUpdatedAt;
    private boolean status;

    // Trả về thông tin vai trò dưới dạng một DTO khác để dễ quản lý
    private AccountRoleResponseDTO role;

}