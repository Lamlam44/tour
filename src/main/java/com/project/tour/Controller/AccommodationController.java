package com.project.tour.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.project.tour.DTO.AccommodationRequestDTO;
import com.project.tour.DTO.AccommodationResponseDTO;
import com.project.tour.Service.AccommodationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accommodations")
@Validated
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @PostMapping
    public ResponseEntity<AccommodationResponseDTO> create(@Valid @RequestBody AccommodationRequestDTO req) {
        AccommodationResponseDTO res = accommodationService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/{id}")
    public AccommodationResponseDTO getById(@PathVariable String id) {
        return accommodationService.getById(id);
    }

    @GetMapping
    public List<AccommodationResponseDTO> getAll() {
        return accommodationService.getAll();
    }

    @PutMapping("/{id}")
    public AccommodationResponseDTO update(@PathVariable String id, @Valid @RequestBody AccommodationRequestDTO req) {
        return accommodationService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        accommodationService.delete(id);
    }
}
