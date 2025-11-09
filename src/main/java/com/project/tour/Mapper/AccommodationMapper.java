package com.project.tour.Mapper;

import com.project.tour.DTO.AccommodationRequestDTO;
import com.project.tour.DTO.AccommodationResponseDTO;
import com.project.tour.Entity.Accommodation;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper này chuyển đổi giữa Accommodation (Entity) và các DTO của nó.
 */
@Mapper(componentModel = "spring") // Đánh dấu đây là một Spring Bean
public interface AccommodationMapper {

    /**
     * Chuyển đổi từ Accommodation (Entity) sang AccommodationResponseDTO.
     * Dùng khi trả dữ liệu về cho client.
     * * MapStruct sẽ tự động map các trường có tên giống hệt nhau:
     * - accommodationId -> accommodationId
     * - accommodationName -> accommodationName
     * - location -> location
     * - v.v...
     */
    AccommodationResponseDTO accommodationToResponseDTO(Accommodation accommodation);

    /**
     * Chuyển đổi từ AccommodationRequestDTO sang Accommodation (Entity).
     * Dùng khi tạo mới một Nơi ở (Accommodation).
     */
    // Chúng ta phải "bỏ qua" (ignore) 2 trường này:
    // 1. "accommodationId": Vì ID này sẽ được tạo tự động ở lớp Service
    // 2. "tours": Vì khi mới tạo, Nơi ở này chưa gắn với Tour nào.
    @Mapping(target = "accommodationId", ignore = true)
    @Mapping(target = "tours", ignore = true)
    Accommodation accommodationRequestDTOToAccommodation(AccommodationRequestDTO requestDTO);

    @Mapping(target = "accommodationId", ignore = true) // 1. Không bao giờ cập nhật ID
    @Mapping(target = "tours", ignore = true) // 2. Không cập nhật danh sách Tour ở đây
    void updateAccommodationFromDto(AccommodationRequestDTO dto, @MappingTarget Accommodation entity);
}