package com.project.tour.Mapper;

import com.project.tour.DTO.TouristDestinationRequestDTO;
import com.project.tour.DTO.TouristDestinationResponseDTO;
import com.project.tour.Entity.TouristDestination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
}