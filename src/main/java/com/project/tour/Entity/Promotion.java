package com.project.tour.Entity;

import java.util.Set;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "promotions")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String promotionId;

    @Column(name = "promotion_name", nullable = false)
    private String promotionName;

    @Column(name = "discount_percentage", nullable = false)
    private Double discountPercentage;

    @Column(name = "start_date", nullable = false)
    private String startDate;

    @Column(name = "end_date", nullable = false)
    private String endDate;
    
    @ManyToMany(mappedBy = "promotions", fetch = FetchType.LAZY)
    private Set<Invoice> invoices;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "tour_promotions",
        joinColumns = @JoinColumn(name = "promotion_id"),
        inverseJoinColumns = @JoinColumn(name = "tour_id")
    )
    private Set<Tour> tours;
}
