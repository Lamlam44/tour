package com.project.tour.Mapper;

import com.project.tour.DTO.TourRequestDTO;
import com.project.tour.DTO.TourResponseDTO;
import com.project.tour.Entity.Tour;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = "spring",
    uses = {
        TourGuideMapper.class,
        AccommodationMapper.class, // <-- Bây giờ sẽ hoạt động đúng
        TravelVehicleMapper.class,
        TouristDestinationMapper.class
    }
)
public interface TourMapper {

    /**
     * Chuyển đổi từ Tour (Entity) sang TourResponseDTO.
     * MapStruct sẽ tự động dùng AccommodationMapper để chuyển
     * "tour.accommodation" (Entity) sang "dto.accommodation" (DTO)
     */
    TourResponseDTO tourToTourResponseDTO(Tour tour);

    /**
     * Chuyển đổi từ TourRequestDTO (Request) sang Tour (Entity).
     */
    @Mapping(target = "tourId", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    @Mapping(target = "promotions", ignore = true)
    @Mapping(target = "tourGuide", ignore = true)
    @Mapping(target = "accommodation", ignore = true) // Sẽ được xử lý ở Service
    @Mapping(target = "travelVehicles", ignore = true)
    @Mapping(target = "touristDestinations", ignore = true)
    Tour tourRequestDTOToTour(TourRequestDTO requestDTO);
    
    @Mapping(target = "tourId", ignore = true) // 1. Không bao giờ cập nhật ID
    @Mapping(target = "invoices", ignore = true) // 2. Giữ nguyên danh sách hóa đơn
    @Mapping(target = "promotions", ignore = true) // 3. Giữ nguyên danh sách khuyến mãi
    @Mapping(target = "tourGuide", ignore = true) // 4. Giữ nguyên hướng dẫn viên
    @Mapping(target = "accommodation", ignore = true) // 5. Giữ nguyên chỗ ở
    @Mapping(target = "travelVehicles", ignore = true) // 6. Giữ nguyên phương tiện
    @Mapping(target = "touristDestinations", ignore = true) // 7. Giữ nguyên điểm đến
    void updateTourFromDto(TourRequestDTO dto, @MappingTarget Tour entity);
}