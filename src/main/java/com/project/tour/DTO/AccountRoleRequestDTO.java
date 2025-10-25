package com.project.tour.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRoleRequestDTO {

    @NotBlank(message = "Tên vai trò không được để trống")
    private String roleName;
}