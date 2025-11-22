package com.project.tour.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.project.tour.DTO.TouristDestinationRequestDTO;
import com.project.tour.DTO.TouristDestinationResponseDTO;
import com.project.tour.Entity.TouristDestination;
import com.project.tour.Repository.TouristDestinationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TouristDestinationService {

    private final TouristDestinationRepository touristDestinationRepository;

    @Transactional
    public TouristDestinationResponseDTO create(TouristDestinationRequestDTO req) {
        TouristDestination entity = new TouristDestination();
        entity.setDestinationName(req.getDestinationName());
        entity.setLocation(req.getLocation());
        entity.setDescription(req.getDescription());
        entity.setEntryFee(req.getEntryFee());

        TouristDestination saved = touristDestinationRepository.save(entity);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public TouristDestinationResponseDTO getById(String id) {
        TouristDestination td = touristDestinationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tourist Destination not found"));
        return toResponse(td);
    }

    @Transactional(readOnly = true)
    public List<TouristDestinationResponseDTO> getAll() {
        return touristDestinationRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TouristDestinationResponseDTO> findByDestinationName(String name) {
        return touristDestinationRepository.findByDestinationNameContainingIgnoreCase(name)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TouristDestinationResponseDTO update(String id, TouristDestinationRequestDTO req) {
        TouristDestination td = touristDestinationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tourist Destination not found"));

        if (req.getDestinationName() != null && !req.getDestinationName().isBlank())
            td.setDestinationName(req.getDestinationName());
        if (req.getLocation() != null && !req.getLocation().isBlank())
            td.setLocation(req.getLocation());
        if (req.getDescription() != null && !req.getDescription().isBlank())
            td.setDescription(req.getDescription());
        if (req.getEntryFee() != null)
            td.setEntryFee(req.getEntryFee());

        TouristDestination saved = touristDestinationRepository.save(td);
        return toResponse(saved);
    }

    @Transactional
    public void delete(String id) {
        if (!touristDestinationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tourist Destination not found");
        }
        touristDestinationRepository.deleteById(id);
    }

    private TouristDestinationResponseDTO toResponse(TouristDestination entity) {
        TouristDestinationResponseDTO dto = new TouristDestinationResponseDTO();
        dto.setDestinationId(entity.getDestinationId());
        dto.setDestinationName(entity.getDestinationName());
        dto.setLocation(entity.getLocation());
        dto.setDescription(entity.getDescription());
        dto.setEntryFee(entity.getEntryFee());
        return dto;
    }
}
