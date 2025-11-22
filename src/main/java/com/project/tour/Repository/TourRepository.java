package com.project.tour.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.tour.Entity.Tour;



import java.util.List;



@Repository

public interface TourRepository extends JpaRepository<Tour, String> {

    List<Tour> findByTourNameContainingIgnoreCase(String tourName);
    @Modifying // Báo cho Spring biết đây là câu lệnh UPDATE/DELETE
    @Query("UPDATE Tour t SET t.remainingSlots = t.remainingSlots - :amount " +
        "WHERE t.id = :tourId AND t.remainingSlots >= :amount")
    int decreaseSlots(@Param("tourId") String tourId, @Param("amount") int amount);
}
