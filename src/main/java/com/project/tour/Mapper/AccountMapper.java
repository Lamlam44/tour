package com.project.tour.Mapper;

import com.project.tour.DTO.AccountRequestDTO;
import com.project.tour.DTO.AccountResponseDTO;
import com.project.tour.Entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
     * Dùng khi trả dữ liệu về cho client.
     *
     * MapStruct sẽ tự động dùng 'AccountRoleMapper' (trong 'uses')
     * để chuyển 'account.role' (Entity) thành 'dto.role' (DTO).
     */
    AccountResponseDTO accountToResponseDTO(Account account);

    /**
     * Chuyển đổi từ AccountRequestDTO sang Account (Entity).
     * Dùng khi tạo mới một Tài khoản.
     *
     * Lưu ý: Mật khẩu (password) sẽ được map nguyên bản (plain text).
     * Lớp Service sẽ có trách nhiệm MÃ HÓA (encode) mật khẩu này
     * trước khi lưu vào database.
     */
    // Chúng ta phải "bỏ qua" (ignore) các trường sau:
    @Mapping(target = "accountId", ignore = true) // 1. ID sẽ được tạo ở Service.
    @Mapping(target = "accountCreatedAt", ignore = true) // 2. Tự động gán bằng @PrePersist.
    @Mapping(target = "accountUpdatedAt", ignore = true) // 3. Tự động gán bằng @PrePersist.
    @Mapping(target = "role", ignore = true) // 4. Sẽ được Service xử lý (từ roleId).
    @Mapping(target = "invoices", ignore = true) // 5. Tài khoản mới không có hóa đơn.
    @Mapping(target = "customer", ignore = true) // 6. Customer sẽ được tạo/gán riêng.
    Account accountRequestDTOToAccount(AccountRequestDTO requestDTO);
}