package com.project.tour.Service;

import com.project.tour.DTO.*;
import com.project.tour.Entity.*;
import com.project.tour.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourService {

    private final TourRepository tourRepository;
    private final TourGuideRepository tourGuideRepository;
    private final AccommodationRepository accommodationRepository;
    private final TravelVehicleRepository travelVehicleRepository;
    private final TouristDestinationRepository touristDestinationRepository;


    @Transactional
    public TourResponseDTO create(TourRequestDTO req) {
        Tour entity = new Tour();
        // Ánh xạ các thuộc tính đơn giản
        entity.setTourName(req.getTourName());
        entity.setTourDescription(req.getTourDescription());
        entity.setTourPrice(req.getTourPrice());
        entity.setTourStatus(req.getTourStatus());
        entity.setTourImage(req.getTourImage());
        entity.setTourStartDate(req.getTourStartDate());
        entity.setTourEndDate(req.getTourEndDate());
        entity.setTourRemainingSlots(req.getTourRemainingSlots());

        // Xử lý và gán các mối quan hệ
        setRelationships(entity, req);

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

    @Transactional(readOnly = true)
    public List<TourResponseDTO> findByTourName(String name) {
        return tourRepository.findByTourNameContainingIgnoreCase(name)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
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

        // Xử lý và gán các mối quan hệ
        setRelationships(t, req);

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

    /**
     * Phương thức trợ giúp để thiết lập các mối quan hệ từ DTO.
     * Tái sử dụng cho cả create và update.
     */
    private void setRelationships(Tour entity, TourRequestDTO dto) {
        // 1. Gán Hướng dẫn viên
        if (dto.getTourGuideId() != null) {
            TourGuide guide = tourGuideRepository.findById(dto.getTourGuideId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "TourGuide with id " + dto.getTourGuideId() + " not found."));
            entity.setTourGuide(guide);
        }

        // 2. Gán Chỗ ở (có thể null)
        if (dto.getAccommodationId() != null) {
            Accommodation accommodation = accommodationRepository.findById(dto.getAccommodationId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Accommodation with id " + dto.getAccommodationId() + " not found."));
            entity.setAccommodation(accommodation);
        } else {
            entity.setAccommodation(null);
        }

        // 3. Gán Phương tiện di chuyển
        if (dto.getTravelVehicleIds() != null && !dto.getTravelVehicleIds().isEmpty()) {
            List<TravelVehicle> vehicles = travelVehicleRepository.findAllById(dto.getTravelVehicleIds());
            if(vehicles.size() != dto.getTravelVehicleIds().size()){
                 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more TravelVehicle ids are invalid.");
            }
            entity.setTravelVehicles(new HashSet<>(vehicles));
        }

        // 4. Gán các Điểm đến du lịch
        if (dto.getTouristDestinationIds() != null && !dto.getTouristDestinationIds().isEmpty()) {
            List<TouristDestination> destinations = touristDestinationRepository.findAllById(dto.getTouristDestinationIds());
             if(destinations.size() != dto.getTouristDestinationIds().size()){
                 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more TouristDestination ids are invalid.");
            }
            entity.setTouristDestinations(new HashSet<>(destinations));
        }
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

        // Map TourGuide
        if (entity.getTourGuide() != null) {
            TourGuideResponseDTO guideDto = new TourGuideResponseDTO();
            guideDto.setTourGuideId(entity.getTourGuide().getTourGuideId());
            guideDto.setTourGuideName(entity.getTourGuide().getTourGuideName());
            dto.setTourGuide(guideDto);
        }

        // Map Accommodation
        if (entity.getAccommodation() != null) {
            AccommodationResponseDTO accommodationDto = new AccommodationResponseDTO();
            accommodationDto.setAccommodationId(entity.getAccommodation().getAccommodationId());
            accommodationDto.setAccommodationName(entity.getAccommodation().getAccommodationName());
            accommodationDto.setRating(entity.getAccommodation().getRating());
            dto.setAccommodation(accommodationDto);
        }

        // Map TravelVehicles
        if (entity.getTravelVehicles() != null) {
            dto.setTravelVehicles(entity.getTravelVehicles().stream().map(v -> {
                TravelVehicleResponseDTO vehicleDto = new TravelVehicleResponseDTO();
                vehicleDto.setVehicleId(v.getVehicleId());
                vehicleDto.setVehicleType(v.getVehicleType());
                return vehicleDto;
            }).collect(Collectors.toSet()));
        }

        // Map TouristDestinations
        if (entity.getTouristDestinations() != null) {
            dto.setTouristDestinations(entity.getTouristDestinations().stream().map(d -> {
                TouristDestinationResponseDTO destDto = new TouristDestinationResponseDTO();
                destDto.setDestinationId(d.getDestinationId());
                destDto.setDestinationName(d.getDestinationName());
                return destDto;
            }).collect(Collectors.toSet()));
        }

        return dto;
    }
}