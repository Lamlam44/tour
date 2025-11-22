package com.project.tour.Repository;

import com.project.tour.Entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, String> {
    List<Accommodation> findByAccommodationNameContainingIgnoreCase(String accommodationName);
}
