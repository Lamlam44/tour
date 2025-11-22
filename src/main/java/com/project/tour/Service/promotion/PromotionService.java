package com.project.tour.Service.promotion;

import com.project.tour.DTO.PromotionRequestDTO;
import com.project.tour.DTO.PromotionResponseDTO;

import java.util.List;

public interface PromotionService {

    PromotionResponseDTO createPromotion(PromotionRequestDTO dto);

    List<PromotionResponseDTO> getAllPromotions();

    List<PromotionResponseDTO> findByPromotionName(String name);

    PromotionResponseDTO getPromotionById(String id);

    PromotionResponseDTO updatePromotion(String id, PromotionRequestDTO dto);

    void deletePromotion(String id);
}
