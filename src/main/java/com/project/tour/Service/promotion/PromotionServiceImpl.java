package com.project.tour.Service.promotion;

import com.project.tour.DTO.PromotionRequestDTO;
import com.project.tour.DTO.PromotionResponseDTO;
import com.project.tour.Entity.Promotion;
import com.project.tour.Entity.Tour;
import com.project.tour.Repository.PromotionRepository;
import com.project.tour.Repository.TourRepository;
import com.project.tour.Service.promotion.PromotionService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final TourRepository tourRepository;

    // ============================================================
    // CREATE
    // ============================================================
    @Override
    public PromotionResponseDTO createPromotion(PromotionRequestDTO dto) {

        Promotion promotion = new Promotion();
        promotion.setPromotionName(dto.getPromotionName());
        promotion.setDiscountPercentage(dto.getDiscountPercentage());
        promotion.setStartDate(dto.getStartDate());
        promotion.setEndDate(dto.getEndDate());
        promotion.setDescription(dto.getDescription());

        // Lấy danh sách tour
        Set<Tour> tours = getToursFromIds(dto.getTourIds());
        promotion.setTours(tours);

        Promotion saved = promotionRepository.save(promotion);
        return convertToDTO(saved);
    }

    // ============================================================
    // GET ALL
    // ============================================================
    @Override
    public List<PromotionResponseDTO> getAllPromotions() {
        return promotionRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ============================================================
    // GET BY ID
    // ============================================================
    @Override
    public PromotionResponseDTO getPromotionById(String id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khuyến mãi: " + id));

        return convertToDTO(promotion);
    }

    // ============================================================
    // UPDATE
    // ============================================================
    @Override
    public PromotionResponseDTO updatePromotion(String id, PromotionRequestDTO dto) {

        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khuyến mãi: " + id));

        promotion.setPromotionName(dto.getPromotionName());
        promotion.setDiscountPercentage(dto.getDiscountPercentage());
        promotion.setStartDate(dto.getStartDate());
        promotion.setEndDate(dto.getEndDate());
        promotion.setDescription(dto.getDescription());

        // Update danh sách tour
        Set<Tour> tours = getToursFromIds(dto.getTourIds());
        promotion.setTours(tours);

        Promotion saved = promotionRepository.save(promotion);
        return convertToDTO(saved);
    }

    // ============================================================
    // DELETE
    // ============================================================
    @Override
    public void deletePromotion(String id) {
        if (!promotionRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy khuyến mãi để xóa: " + id);
        }
        promotionRepository.deleteById(id);
    }

    // ============================================================
    // HÀM HỖ TRỢ
    // ============================================================
    private Set<Tour> getToursFromIds(Set<String> tourIds) {

        if (tourIds == null || tourIds.isEmpty()) {
            return new HashSet<>(); // Không bắt buộc phải có tour
        }

        List<Tour> tours = tourRepository.findAllById(tourIds);

        if (tours.size() != tourIds.size()) {
            throw new RuntimeException("Một hoặc nhiều tour không tồn tại");
        }

        return new HashSet<>(tours);
    }

    private PromotionResponseDTO convertToDTO(Promotion promotion) {

        PromotionResponseDTO dto = new PromotionResponseDTO();

        dto.setPromotionId(promotion.getPromotionId());
        dto.setPromotionName(promotion.getPromotionName());
        dto.setDiscountPercentage(promotion.getDiscountPercentage());
        dto.setStartDate(promotion.getStartDate());
        dto.setEndDate(promotion.getEndDate());
        dto.setDescription(promotion.getDescription());

        // Danh sách tour áp dụng (ID + Name)
        Set<Map<String, String>> appliedTours = promotion.getTours()
                .stream()
                .map(t -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("tourId", t.getTourId());
                    map.put("tourName", t.getTourName());
                    return map;
                })
                .collect(Collectors.toSet());

        dto.setAppliedTours(appliedTours);

        return dto;
    }
}
