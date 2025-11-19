package com.project.tour.Repository;

import com.project.tour.Entity.TravelVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelVehicleRepository extends JpaRepository<TravelVehicle, String> {
}
