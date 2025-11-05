package com.project.tour.Mapper;

import com.project.tour.DTO.CustomerRequestDTO;
import com.project.tour.DTO.CustomerResponseDTO;
import com.project.tour.Entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper này chuyển đổi giữa Customer (Entity) và các DTO của nó.
 */
@Mapper(
    componentModel = "spring",
    // Cần AccountMapper vì CustomerResponseDTO có chứa AccountResponseDTO
    uses = {AccountMapper.class} 
)
public interface CustomerMapper {

    /**
     * Chuyển đổi từ Customer (Entity) sang CustomerResponseDTO.
     * Dùng khi trả dữ liệu về cho client.
     *
     * MapStruct sẽ tự động dùng 'AccountMapper' (trong 'uses')
     * để chuyển 'customer.account' (Entity) thành 'dto.account' (DTO).
     */
    CustomerResponseDTO customerToResponseDTO(Customer customer);

    /**
     * Chuyển đổi từ CustomerRequestDTO sang Customer (Entity).
     * Dùng khi tạo mới một Khách hàng.
     */
    // "customerId" sẽ được tạo ở Service.
    // "account" (Entity) sẽ được Service xử lý bằng cách
    // tìm Account từ "accountId" (String) trong DTO.
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "account", ignore = true)
    Customer customerRequestDTOToCustomer(CustomerRequestDTO requestDTO);
}