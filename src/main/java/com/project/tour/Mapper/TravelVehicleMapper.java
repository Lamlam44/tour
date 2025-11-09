package com.project.tour.Mapper;

import com.project.tour.DTO.TravelVehicleRequestDTO;
import com.project.tour.DTO.TravelVehicleResponseDTO;
import com.project.tour.Entity.TravelVehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper này chuyển đổi giữa TravelVehicle (Entity) và các DTO của nó.
 */
@Mapper(componentModel = "spring") // Đánh dấu đây là một Spring Bean
public interface TravelVehicleMapper {

    /**
     * Chuyển đổi từ TravelVehicle (Entity) sang TravelVehicleResponseDTO.
     * Dùng khi trả dữ liệu về cho client.
     * MapStruct tự động map tất cả các trường vì tên khớp 100%.
     */
    TravelVehicleResponseDTO travelVehicleToResponseDTO(TravelVehicle travelVehicle);

    /**
     * Chuyển đổi từ TravelVehicleRequestDTO sang TravelVehicle (Entity).
     * Dùng khi tạo mới một Phương tiện.
     */
    // "vehicleId" sẽ được tạo ở Service.
    // "tours" (danh sách tour) không liên quan khi tạo Phương tiện mới.
    @Mapping(target = "vehicleId", ignore = true)
    @Mapping(target = "tours", ignore = true)
    TravelVehicle travelVehicleRequestDTOToVehicle(TravelVehicleRequestDTO requestDTO);

    @Mapping(target = "vehicleId", ignore = true) // 1. Không bao giờ cập nhật ID
    @Mapping(target = "tours", ignore = true) // 2. Không cập nhật danh sách tour ở đây
    void updateVehicleFromDto(TravelVehicleRequestDTO dto, @MappingTarget TravelVehicle entity);
}