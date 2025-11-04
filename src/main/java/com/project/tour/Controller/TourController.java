package com.project.tour.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.project.tour.DTO.TourRequestDTO;
import com.project.tour.DTO.TourResponseDTO;
import com.project.tour.Service.TourService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tours")
@Validated
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    @PostMapping
    public ResponseEntity<TourResponseDTO> create(@Valid @RequestBody TourRequestDTO req) {
        TourResponseDTO res = tourService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/{id}")
    public TourResponseDTO getById(@PathVariable String id) {
        return tourService.getById(id);
    }

    @GetMapping
    public List<TourResponseDTO> getAll() {
        return tourService.getAll();
    }

    @PutMapping("/{id}")
    public TourResponseDTO update(@PathVariable String id, @Valid @RequestBody TourRequestDTO req) {
        return tourService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        tourService.delete(id);
    }
}
