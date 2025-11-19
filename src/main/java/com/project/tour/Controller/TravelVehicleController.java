package com.project.tour.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.project.tour.DTO.TravelVehicleRequestDTO;
import com.project.tour.DTO.TravelVehicleResponseDTO;
import com.project.tour.Service.TravelVehicleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/travel-vehicles")
@Validated
@RequiredArgsConstructor
public class TravelVehicleController {

    private final TravelVehicleService travelVehicleService;

    @PostMapping
    public ResponseEntity<TravelVehicleResponseDTO> create(@Valid @RequestBody TravelVehicleRequestDTO req) {
        TravelVehicleResponseDTO res = travelVehicleService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/{id}")
    public TravelVehicleResponseDTO getById(@PathVariable String id) {
        return travelVehicleService.getById(id);
    }

    @GetMapping
    public List<TravelVehicleResponseDTO> getAll() {
        return travelVehicleService.getAll();
    }

    @PutMapping("/{id}")
    public TravelVehicleResponseDTO update(@PathVariable String id, @Valid @RequestBody TravelVehicleRequestDTO req) {
        return travelVehicleService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        travelVehicleService.delete(id);
    }
}
