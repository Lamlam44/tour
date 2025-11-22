package com.project.tour.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.project.tour.DTO.AccommodationRequestDTO;
import com.project.tour.DTO.AccommodationResponseDTO;
import com.project.tour.Entity.Accommodation;
import com.project.tour.Repository.AccommodationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    @Transactional
    public AccommodationResponseDTO create(AccommodationRequestDTO req) {
        Accommodation entity = new Accommodation();
        entity.setAccommodationName(req.getAccommodationName());
        entity.setLocation(req.getLocation());
        entity.setRating(req.getRating());
        entity.setPricePerNight(req.getPricePerNight());
        entity.setAccommodationType(req.getAccommodationType());

        Accommodation saved = accommodationRepository.save(entity);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public AccommodationResponseDTO getById(String id) {
        Accommodation a = accommodationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Accommodation not found"));
        return toResponse(a);
    }

    @Transactional(readOnly = true)
    public List<AccommodationResponseDTO> getAll() {
        return accommodationRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AccommodationResponseDTO> findByAccommodationName(String name) {
        return accommodationRepository.findByAccommodationNameContainingIgnoreCase(name)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public AccommodationResponseDTO update(String id, AccommodationRequestDTO req) {
        Accommodation a = accommodationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Accommodation not found"));

        if (req.getAccommodationName() != null && !req.getAccommodationName().isBlank())
            a.setAccommodationName(req.getAccommodationName());
        if (req.getLocation() != null && !req.getLocation().isBlank())
            a.setLocation(req.getLocation());
        if (req.getRating() != null)
            a.setRating(req.getRating());
        if (req.getPricePerNight() != null)
            a.setPricePerNight(req.getPricePerNight());
        if (req.getAccommodationType() != null && !req.getAccommodationType().isBlank())
            a.setAccommodationType(req.getAccommodationType());

        Accommodation saved = accommodationRepository.save(a);
        return toResponse(saved);
    }

    @Transactional
    public void delete(String id) {
        if (!accommodationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Accommodation not found");
        }
        accommodationRepository.deleteById(id);
    }

    private AccommodationResponseDTO toResponse(Accommodation entity) {
        AccommodationResponseDTO dto = new AccommodationResponseDTO();
        dto.setAccommodationId(entity.getAccommodationId());
        dto.setAccommodationName(entity.getAccommodationName());
        dto.setLocation(entity.getLocation());
        dto.setRating(entity.getRating());
        dto.setPricePerNight(entity.getPricePerNight());
        dto.setAccommodationType(entity.getAccommodationType());
        return dto;
    }
}
