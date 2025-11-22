package com.project.tour.Repository;

import com.project.tour.Entity.TouristDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TouristDestinationRepository extends JpaRepository<TouristDestination, String> {
    List<TouristDestination> findByDestinationNameContainingIgnoreCase(String destinationName);
}
