package com.project.tour.Mapper;

import com.project.tour.DTO.PromotionRequestDTO;
import com.project.tour.DTO.PromotionResponseDTO;
import com.project.tour.Entity.Promotion;
import com.project.tour.Entity.Tour; // Bắt buộc import Tour
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map; // Import Map
import java.util.Set; // Import Set
import java.util.stream.Collectors; // Import Collectors

/**
 * Mapper này chuyển đổi giữa Promotion (Entity) và các DTO của nó.
 */
@Mapper(componentModel = "spring")
public interface PromotionMapper {

    /**
     * Chuyển đổi từ Promotion (Entity) sang PromotionResponseDTO.
     * Dùng khi trả dữ liệu về cho client.
     */
    // Báo cho MapStruct: "lấy 'tours' từ Entity và gán vào 'appliedTours' trong DTO"
    // MapStruct sẽ tự động tìm phương thức "mapToursToAppliedTours" bên dưới
    // để thực hiện việc chuyển đổi phức tạp này.
    @Mapping(source = "tours", target = "appliedTours")
    PromotionResponseDTO promotionToResponseDTO(Promotion promotion);

    /**
     * Chuyển đổi từ PromotionRequestDTO sang Promotion (Entity).
     * Dùng khi tạo mới một Khuyến mãi.
     */
    @Mapping(target = "promotionId", ignore = true) // Sẽ được tạo ở Service
    @Mapping(target = "invoices", ignore = true) // Không liên quan khi tạo
    @Mapping(target = "tours", ignore = true) // Service sẽ xử lý từ tourIds
    Promotion promotionRequestDTOToPromotion(PromotionRequestDTO requestDTO);

    /**
     * PHƯƠNG THỨC TÙY CHỈNH (CUSTOM METHOD)
     * * MapStruct không biết cách tự động chuyển từ Set<Tour> (Entity)
     * sang Set<Map<String, String>> (DTO).
     *
     * Chúng ta viết hàm "default" này để "dạy" nó:
     * "Khi thấy Set<Tour>, hãy chạy hàm này để biến nó thành Set<Map<...>>"
     */
    default Set<Map<String, String>> mapToursToAppliedTours(Set<Tour> tours) {
        if (tours == null) {
            return null; // Hoặc trả về một Set rỗng: java.util.Collections.emptySet();
        }

        // Chuyển đổi Set<Tour> thành Set<Map<String, String>>
        return tours.stream()
                .map(tour -> {
                    // Với mỗi Tour, tạo một Map chứa id và tên
                    Map<String, String> tourMap = new java.util.HashMap<>();
                    tourMap.put("id", tour.getTourId());
                    tourMap.put("name", tour.getTourName());
                    return tourMap;
                })
                .collect(Collectors.toSet());
    }
}