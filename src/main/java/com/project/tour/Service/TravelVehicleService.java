package com.project.tour.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.project.tour.DTO.TravelVehicleRequestDTO;
import com.project.tour.DTO.TravelVehicleResponseDTO;
import com.project.tour.Entity.TravelVehicle;
import com.project.tour.Repository.TravelVehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelVehicleService {

    private final TravelVehicleRepository travelVehicleRepository;

    @Transactional
    public TravelVehicleResponseDTO create(TravelVehicleRequestDTO req) {
        TravelVehicle entity = new TravelVehicle();
        entity.setVehicleType(req.getVehicleType());
        entity.setCapacity(req.getCapacity());
        entity.setRentalPricePerDay(req.getRentalPricePerDay());

        TravelVehicle saved = travelVehicleRepository.save(entity);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public TravelVehicleResponseDTO getById(String id) {
        TravelVehicle tv = travelVehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Travel Vehicle not found"));
        return toResponse(tv);
    }

    @Transactional(readOnly = true)
    public List<TravelVehicleResponseDTO> getAll() {
        return travelVehicleRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public TravelVehicleResponseDTO update(String id, TravelVehicleRequestDTO req) {
        TravelVehicle tv = travelVehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Travel Vehicle not found"));

        if (req.getVehicleType() != null && !req.getVehicleType().isBlank())
            tv.setVehicleType(req.getVehicleType());
        if (req.getCapacity() > 0)
            tv.setCapacity(req.getCapacity());
        if (req.getRentalPricePerDay() != null)
            tv.setRentalPricePerDay(req.getRentalPricePerDay());

        TravelVehicle saved = travelVehicleRepository.save(tv);
        return toResponse(saved);
    }

    @Transactional
    public void delete(String id) {
        if (!travelVehicleRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Travel Vehicle not found");
        }
        travelVehicleRepository.deleteById(id);
    }

    private TravelVehicleResponseDTO toResponse(TravelVehicle entity) {
        TravelVehicleResponseDTO dto = new TravelVehicleResponseDTO();
        dto.setVehicleId(entity.getVehicleId());
        dto.setVehicleType(entity.getVehicleType());
        dto.setCapacity(entity.getCapacity());
        dto.setRentalPricePerDay(entity.getRentalPricePerDay());
        return dto;
    }
}
