package com.project.tour.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.project.tour.DTO.TourRequestDTO;
import com.project.tour.DTO.TourResponseDTO;
import com.project.tour.Entity.Tour;
import com.project.tour.Repository.TourRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourService {

    private final TourRepository tourRepository;

    @Transactional
    public TourResponseDTO create(TourRequestDTO req) {
        Tour entity = new Tour();
        entity.setTourName(req.getTourName());
        entity.setTourDescription(req.getTourDescription());
        entity.setTourPrice(req.getTourPrice());
        entity.setTourStatus(req.getTourStatus());
        entity.setTourImage(req.getTourImage());
        entity.setTourStartDate(req.getTourStartDate());
        entity.setTourEndDate(req.getTourEndDate());
        entity.setTourRemainingSlots(req.getTourRemainingSlots());

        Tour saved = tourRepository.save(entity);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public TourResponseDTO getById(String id) {
        Tour t = tourRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour not found"));
        return toResponse(t);
    }

    @Transactional(readOnly = true)
    public List<TourResponseDTO> getAll() {
        return tourRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public TourResponseDTO update(String id, TourRequestDTO req) {
        Tour t = tourRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour not found"));

        if (req.getTourName() != null && !req.getTourName().isBlank())
            t.setTourName(req.getTourName());
        if (req.getTourDescription() != null && !req.getTourDescription().isBlank())
            t.setTourDescription(req.getTourDescription());
        if (req.getTourPrice() <= 0)
            t.setTourPrice(req.getTourPrice());
        if (req.getTourStatus() != null && !req.getTourStatus().isBlank())
            t.setTourStatus(req.getTourStatus());
        if (req.getTourImage() != null && !req.getTourImage().isBlank())
            t.setTourImage(req.getTourImage());
        if (req.getTourStartDate() != null)
            t.setTourStartDate(req.getTourStartDate());
        if (req.getTourEndDate() != null)
            t.setTourEndDate(req.getTourEndDate());
        if (req.getTourRemainingSlots() <= 0)
            t.setTourRemainingSlots(req.getTourRemainingSlots());

        Tour saved = tourRepository.save(t);
        return toResponse(saved);
    }

    @Transactional
    public void delete(String id) {
        if (!tourRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour not found");
        }
        tourRepository.deleteById(id);
    }

    private TourResponseDTO toResponse(Tour entity) {
        TourResponseDTO dto = new TourResponseDTO();
        dto.setTourId(entity.getTourId());
        dto.setTourName(entity.getTourName());
        dto.setTourDescription(entity.getTourDescription());
        dto.setTourPrice(entity.getTourPrice());
        dto.setTourStatus(entity.getTourStatus());
        dto.setTourImage(entity.getTourImage());
        dto.setTourStartDate(entity.getTourStartDate());
        dto.setTourEndDate(entity.getTourEndDate());
        dto.setTourRemainingSlots(entity.getTourRemainingSlots());
        return dto;
    }
}
