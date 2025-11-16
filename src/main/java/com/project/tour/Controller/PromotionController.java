package com.project.tour.Controller;

import com.project.tour.DTO.PromotionRequestDTO;
import com.project.tour.DTO.PromotionResponseDTO;
import com.project.tour.Service.PromotionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PromotionController {

    private final PromotionService promotionService;

    // ===========================
    // CREATE
    // ===========================
    @PostMapping
    public ResponseEntity<PromotionResponseDTO> createPromotion(
            @Valid @RequestBody PromotionRequestDTO requestDTO) {

        PromotionResponseDTO response = promotionService.createPromotion(requestDTO);
        return ResponseEntity.ok(response);
    }

    // ===========================
    // GET ALL
    // ===========================
    @GetMapping
    public ResponseEntity<List<PromotionResponseDTO>> getAllPromotions() {
        return ResponseEntity.ok(promotionService.getAllPromotions());
    }

    // ===========================
    // GET BY ID
    // ===========================
    @GetMapping("/{id}")
    public ResponseEntity<PromotionResponseDTO> getPromotionById(@PathVariable String id) {
        return ResponseEntity.ok(promotionService.getPromotionById(id));
    }

    // ===========================
    // UPDATE
    // ===========================
    @PutMapping("/{id}")
    public ResponseEntity<PromotionResponseDTO> updatePromotion(
            @PathVariable String id,
            @Valid @RequestBody PromotionRequestDTO requestDTO) {

        PromotionResponseDTO response = promotionService.updatePromotion(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    // ===========================
    // DELETE
    // ===========================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePromotion(@PathVariable String id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.ok("Xóa khuyến mãi thành công: " + id);
    }

}
