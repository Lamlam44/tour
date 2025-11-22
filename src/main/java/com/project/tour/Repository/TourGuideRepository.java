package com.project.tour.Repository;

import com.project.tour.Entity.TourGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourGuideRepository extends JpaRepository<TourGuide, String> {
    List<TourGuide> findByTourGuideNameContainingIgnoreCase(String guideName);
}
