package com.project.tour.DTO;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponseDTO {

    private String customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;
    private LocalDate customerDateOfBirth;
    
    // Chỉ trả về thông tin tóm lược của tài khoản
    private AccountResponseDTO account;
}