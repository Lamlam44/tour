package com.project.tour.Mapper;

import com.project.tour.DTO.AccountRoleRequestDTO;
import com.project.tour.DTO.AccountRoleResponseDTO;
import com.project.tour.Entity.AccountRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper này chuyển đổi giữa AccountRole (Entity) và các DTO của nó.
 */
@Mapper(componentModel = "spring") // Đánh dấu đây là một Spring Bean
public interface AccountRoleMapper {

    /**
     * Chuyển đổi từ AccountRole (Entity) sang AccountRoleResponseDTO.
     * Dùng khi trả dữ liệu về cho client.
     * MapStruct tự động map "accountRoleId" và "roleName".
     */
    AccountRoleResponseDTO accountRoleToResponseDTO(AccountRole accountRole);

    /**
     * Chuyển đổi từ AccountRoleRequestDTO sang AccountRole (Entity).
     * Dùng khi tạo mới một Vai trò.
     */
    // "accountRoleId" sẽ được tạo ở Service.
    // "accounts" (danh sách tài khoản) không liên quan khi tạo vai trò mới.
    @Mapping(target = "accountRoleId", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    AccountRole accountRoleRequestDTOToAccountRole(AccountRoleRequestDTO requestDTO);
}