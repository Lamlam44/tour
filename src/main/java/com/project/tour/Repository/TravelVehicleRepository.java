package com.project.tour.Repository;

import com.project.tour.Entity.TravelVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelVehicleRepository extends JpaRepository<TravelVehicle, String> {
    List<TravelVehicle> findByVehicleTypeContainingIgnoreCase(String vehicleName);
}
