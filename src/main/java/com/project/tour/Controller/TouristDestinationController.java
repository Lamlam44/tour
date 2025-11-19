package com.project.tour.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.project.tour.DTO.TouristDestinationRequestDTO;
import com.project.tour.DTO.TouristDestinationResponseDTO;
import com.project.tour.Service.TouristDestinationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tourist-destinations")
@Validated
@RequiredArgsConstructor
public class TouristDestinationController {

    private final TouristDestinationService touristDestinationService;

    @PostMapping
    public ResponseEntity<TouristDestinationResponseDTO> create(@Valid @RequestBody TouristDestinationRequestDTO req) {
        TouristDestinationResponseDTO res = touristDestinationService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/{id}")
    public TouristDestinationResponseDTO getById(@PathVariable String id) {
        return touristDestinationService.getById(id);
    }

    @GetMapping
    public List<TouristDestinationResponseDTO> getAll() {
        return touristDestinationService.getAll();
    }

    @PutMapping("/{id}")
    public TouristDestinationResponseDTO update(@PathVariable String id,
            @Valid @RequestBody TouristDestinationRequestDTO req) {
        return touristDestinationService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        touristDestinationService.delete(id);
    }
}
