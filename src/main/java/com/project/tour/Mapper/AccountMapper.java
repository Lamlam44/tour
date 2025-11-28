package com.project.tour.Mapper;

import com.project.tour.DTO.AccountRequestDTO;
import com.project.tour.DTO.AccountResponseDTO;
import com.project.tour.Entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget; // <-- Import quan trọng được thêm vào

/**
 * Mapper này chuyển đổi giữa Account (Entity) và các DTO của nó.
 * Chúng ta giả định rằng bạn cũng sẽ tạo một 'AccountRoleMapper'
 * để xử lý việc map 'AccountRole' sang 'AccountRoleResponseDTO'.
 */
@Mapper(
    componentModel = "spring",
    uses = {AccountRoleMapper.class} // Báo cho MapStruct dùng mapper con
)
public interface AccountMapper {

    /**
     * Chuyển đổi từ Account (Entity) sang AccountResponseDTO.
     * Dùng khi trả dữ liệu về cho client (ĐỌC).
     */
    AccountResponseDTO accountToResponseDTO(Account account);

    /**
     * Chuyển đổi từ AccountRequestDTO sang Account (Entity) MỚI.
     * Dùng khi TẠO MỚI một Tài khoản.
     *
     * Lưu ý: Các trường logic (password, role) sẽ được Service
     * xử lý và ghi đè sau khi gọi hàm map này.
     */
    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "accountCreatedAt", ignore = true)
    @Mapping(target = "accountUpdatedAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "verificationToken", ignore = true)
    @Mapping(target = "verificationTokenExpiry", ignore = true)
    // 'password' có thể được map, Service sẽ mã hóa và ghi đè sau
    Account accountRequestDTOToAccount(AccountRequestDTO requestDTO);

    /**
     * (PHƯƠNG THỨC MỚI)
     * Cập nhật một 'Account' (entity) ĐÃ TỒN TẠI từ 'AccountRequestDTO'.
     * Dùng @MappingTarget để báo MapStruct "đổ" dữ liệu từ DTO
     * vào 'entity' đã tồn tại, thay vì tạo một 'entity' mới (CẬP NHẬT).
     */
    @Mapping(target = "accountId", ignore = true) // 1. Không bao giờ cập nhật ID
    @Mapping(target = "accountCreatedAt", ignore = true) // 2. Không cập nhật ngày tạo
    @Mapping(target = "accountUpdatedAt", ignore = true) // 3. Sẽ được @PreUpdate xử lý
    @Mapping(target = "role", ignore = true) // 4. Service sẽ set role riêng (từ roleId)
    @Mapping(target = "password", ignore = true) // 5. Service sẽ mã hóa và set riêng
    @Mapping(target = "invoices", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "emailVerified", ignore = true) // 6. Không cập nhật trường này từ DTO
    @Mapping(target = "verificationToken", ignore = true) // 7. Không cập nhật trường này từ DTO
    @Mapping(target = "verificationTokenExpiry", ignore = true) // 8. Không cập nhật trường này từ DTO

    void updateAccountFromDto(AccountRequestDTO dto, @MappingTarget Account entity);
}