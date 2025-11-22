package com.project.tour.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.project.tour.DTO.TourGuideRequestDTO;
import com.project.tour.DTO.TourGuideResponseDTO;
import com.project.tour.Service.TourGuideService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tour-guides")
@Validated
@RequiredArgsConstructor
public class TourGuideController {

    private final TourGuideService tourGuideService;

    @PostMapping
    public ResponseEntity<TourGuideResponseDTO> create(@Valid @RequestBody TourGuideRequestDTO req) {
        TourGuideResponseDTO res = tourGuideService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/{id}")
    public TourGuideResponseDTO getById(@PathVariable String id) {
        return tourGuideService.getById(id);
    }

    @GetMapping
    public List<TourGuideResponseDTO> getAll() {
        return tourGuideService.getAll();
    }

    @GetMapping("/search")
    public List<TourGuideResponseDTO> searchTourGuides(@RequestParam String name) {
        return tourGuideService.findByGuideName(name);
    }

    @PutMapping("/{id}")
    public TourGuideResponseDTO update(@PathVariable String id, @Valid @RequestBody TourGuideRequestDTO req) {
        return tourGuideService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        tourGuideService.delete(id);
    }
}
