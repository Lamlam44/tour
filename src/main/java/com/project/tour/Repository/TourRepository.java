package com.project.tour.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tour.Entity.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour, String> {
    
}