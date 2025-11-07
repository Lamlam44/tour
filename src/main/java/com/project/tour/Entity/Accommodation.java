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
@Table(name = "accommodations")
@SuppressWarnings("deprecation") // Tắt cảnh báo "deprecated"
public class Accommodation {
    
    @Id
    @Column(length = 10) // 4 chữ + 6 số = 10
    
    // Dùng @GeneratedValue và @GenericGenerator
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accommodation_id_gen")
    @GenericGenerator(
        name = "accommodation_id_gen", // Tên của generator
        
        // **THAY ĐỔI QUAN TRỌNG**: 
        // Trỏ thẳng "strategy" đến LỚP JAVA của bạn
        strategy = "com.project.tour.StringPrefixedSequenceIdGenerator", 
        
        // Truyền tham số cho class Java
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.SEQUENCE_PARAM, value = "accommodation_seq"), // Tên sequence trong CSDL
            @Parameter(name = StringPrefixedSequenceIdGenerator.PREFIX_PARAM, value = "ACCO"), // 4 chữ cái
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAM, value = "%06d") // 6 số
        }
    )
    private String accommodationId;

    @Column(name = "accommodation_name", nullable = false)
    private String accommodationName;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "price_per_night")
    private Double pricePerNight;

    @Column(name = "accommodation_type", nullable = false)
    private String accommodationType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "accommodation")
    private Set<Tour> tours;
}