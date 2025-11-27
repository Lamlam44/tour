package com.project.tour.Entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

// Thêm các import này
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.project.tour.StringPrefixedSequenceIdGenerator;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "travel_vehicles")
@SuppressWarnings("deprecation") // Tắt cảnh báo "deprecated"
public class TravelVehicle {
    
    @Id
    @Column(length = 10) // 4 chữ + 6 số = 10
    
    // Dùng @GeneratedValue và @GenericGenerator
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_id_gen")
    @GenericGenerator(
        name = "vehicle_id_gen", // Tên của generator
        
        // **THAY ĐỔI QUAN TRỌNG**: 
        // Trỏ thẳng "strategy" đến LỚP JAVA của bạn
        strategy = "com.project.tour.StringPrefixedSequenceIdGenerator", 
        
        // Truyền tham số cho class Java
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.SEQUENCE_PARAM, value = "vehicle_seq"), // Tên sequence trong CSDL
            @Parameter(name = StringPrefixedSequenceIdGenerator.PREFIX_PARAM, value = "VEHI"), // 4 chữ cái
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAM, value = "%06d") // 6 số
        }
    )
    private String vehicleId;

    @Column(name = "vehicle_type", nullable = false)
    private String vehicleType;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "rental_price_per_day", nullable = false)
    private Double rentalPricePerDay;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "travelVehicles")
    private Set<Tour> tours;
}