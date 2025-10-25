package com.project.tour.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceRequestDTO {

    @NotBlank(message = "Trạng thái không được để trống")
    private String status;

    @NotNull(message = "Cần chỉ định tour")
    private String tourId; // Giả sử tourId là String như trong Tour Entity của bạn

    @NotNull(message = "Cần liên kết với một tài khoản")
    private Long accountId; // Giả sử accountId là Long như trong Account Entity

    // Client chỉ cần gửi danh sách ID của các khuyến mãi
    private Set<Long> promotionIds; // Giả sử promotionId là Long
}