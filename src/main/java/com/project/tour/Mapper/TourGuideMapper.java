package com.project.tour.Mapper;

import com.project.tour.DTO.TourGuideRequestDTO;
import com.project.tour.DTO.TourGuideResponseDTO;
import com.project.tour.Entity.TourGuide;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper này chuyển đổi giữa TourGuide (Entity) và các DTO của nó.
 */
@Mapper(componentModel = "spring") // Đánh dấu đây là một Spring Bean
public interface TourGuideMapper {

    /**
     * Chuyển đổi từ TourGuide (Entity) sang TourGuideResponseDTO.
     * Dùng khi trả dữ liệu về cho client.
     * MapStruct tự động map tất cả các trường vì tên khớp 100%.
     */
    TourGuideResponseDTO tourGuideToResponseDTO(TourGuide tourGuide);

    /**
     * Chuyển đổi từ TourGuideRequestDTO sang TourGuide (Entity).
     * Dùng khi tạo mới một Hướng dẫn viên.
     */
    // "tourGuideId" sẽ được tạo ở Service.
    // "tours" (danh sách tour) không liên quan khi tạo Hướng dẫn viên mới.
    @Mapping(target = "tourGuideId", ignore = true)
    @Mapping(target = "tours", ignore = true)
    TourGuide tourGuideRequestDTOToTourGuide(TourGuideRequestDTO requestDTO);

    @Mapping(target = "tourGuideId", ignore = true) // 1. Không bao giờ cập nhật ID
    @Mapping(target = "tours", ignore = true) // 2. Không cập nhật danh sách tour ở đây
    void updateTourGuideFromDto(TourGuideRequestDTO dto, @MappingTarget TourGuide entity);
}