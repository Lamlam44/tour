package com.project.tour.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tour.Entity.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {
    
}