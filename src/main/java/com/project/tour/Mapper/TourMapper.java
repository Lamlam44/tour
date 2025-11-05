package com.project.tour.Mapper;

import com.project.tour.DTO.TourRequestDTO;
import com.project.tour.DTO.TourResponseDTO;
import com.project.tour.Entity.Tour;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
    
}