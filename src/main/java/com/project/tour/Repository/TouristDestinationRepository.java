package com.project.tour.Repository;

import com.project.tour.Entity.TouristDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TouristDestinationRepository extends JpaRepository<TouristDestination, String> {
}
