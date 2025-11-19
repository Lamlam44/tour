package com.project.tour.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.project.tour.DTO.TourGuideRequestDTO;
import com.project.tour.DTO.TourGuideResponseDTO;
import com.project.tour.Entity.TourGuide;
import com.project.tour.Repository.TourGuideRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourGuideService {

    private final TourGuideRepository tourGuideRepository;

    @Transactional
    public TourGuideResponseDTO create(TourGuideRequestDTO req) {
        TourGuide entity = new TourGuide();
        entity.setTourGuideName(req.getTourGuideName());
        entity.setTourGuideEmail(req.getTourGuideEmail());
        entity.setTourGuidePhone(req.getTourGuidePhone());
        entity.setTourGuideExperienceYears(req.getTourGuideExperienceYears());

        TourGuide saved = tourGuideRepository.save(entity);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public TourGuideResponseDTO getById(String id) {
        TourGuide tg = tourGuideRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour Guide not found"));
        return toResponse(tg);
    }

    @Transactional(readOnly = true)
    public List<TourGuideResponseDTO> getAll() {
        return tourGuideRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public TourGuideResponseDTO update(String id, TourGuideRequestDTO req) {
        TourGuide tg = tourGuideRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour Guide not found"));

        if (req.getTourGuideName() != null && !req.getTourGuideName().isBlank())
            tg.setTourGuideName(req.getTourGuideName());
        if (req.getTourGuideEmail() != null && !req.getTourGuideEmail().isBlank())
            tg.setTourGuideEmail(req.getTourGuideEmail());
        if (req.getTourGuidePhone() != null && !req.getTourGuidePhone().isBlank())
            tg.setTourGuidePhone(req.getTourGuidePhone());
        if (req.getTourGuideExperienceYears() >= 0)
            tg.setTourGuideExperienceYears(req.getTourGuideExperienceYears());

        TourGuide saved = tourGuideRepository.save(tg);
        return toResponse(saved);
    }

    @Transactional
    public void delete(String id) {
        if (!tourGuideRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour Guide not found");
        }
        tourGuideRepository.deleteById(id);
    }

    private TourGuideResponseDTO toResponse(TourGuide entity) {
        TourGuideResponseDTO dto = new TourGuideResponseDTO();
        dto.setTourGuideId(entity.getTourGuideId());
        dto.setTourGuideName(entity.getTourGuideName());
        dto.setTourGuideEmail(entity.getTourGuideEmail());
        dto.setTourGuidePhone(entity.getTourGuidePhone());
        dto.setTourGuideExperienceYears(entity.getTourGuideExperienceYears());
        return dto;
    }
}
