package com.project.tour.Mapper;

import com.project.tour.DTO.TouristDestinationRequestDTO;
import com.project.tour.DTO.TouristDestinationResponseDTO;
import com.project.tour.Entity.TouristDestination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper này chuyển đổi giữa TouristDestination (Entity) và các DTO của nó.
 */
@Mapper(componentModel = "spring") // Đánh dấu đây là một Spring Bean
public interface TouristDestinationMapper {

    /**
     * Chuyển đổi từ TouristDestination (Entity) sang TouristDestinationResponseDTO.
     * Dùng khi trả dữ liệu về cho client.
     * MapStruct tự động map tất cả các trường vì tên khớp 100%.
     */
    TouristDestinationResponseDTO destinationToResponseDTO(TouristDestination destination);

    /**
     * Chuyển đổi từ TouristDestinationRequestDTO sang TouristDestination (Entity).
     * Dùng khi tạo mới một Điểm đến.
     */
    // "destinationId" sẽ được tạo ở Service.
    // "tours" (danh sách tour) không liên quan khi tạo Điểm đến mới.
    @Mapping(target = "destinationId", ignore = true)
    @Mapping(target = "tours", ignore = true)
    TouristDestination destinationRequestDTOToDestination(TouristDestinationRequestDTO requestDTO);

    @Mapping(target = "destinationId", ignore = true) // 1. Không bao giờ cập nhật ID
    @Mapping(target = "tours", ignore = true) // 2. Không cập nhật danh sách tour ở đây
    void updateTouristDestinationFromDto(TouristDestinationRequestDTO dto, @MappingTarget TouristDestination entity);
}