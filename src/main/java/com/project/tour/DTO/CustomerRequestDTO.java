package com.project.tour.DTO;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequestDTO {

    @NotBlank(message = "Tên khách hàng không được để trống")
    private String customerName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String customerEmail;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String customerPhone;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String customerAddress;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là một ngày trong quá khứ")
    private LocalDate customerDateOfBirth;

    @NotNull(message = "Cần liên kết với một tài khoản")
    private String accountId;
}