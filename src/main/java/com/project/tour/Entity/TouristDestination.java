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
@Table(name = "tourist_destinations")
@SuppressWarnings("deprecation") // Tắt cảnh báo "deprecated"
public class TouristDestination {
    
    @Id
    @Column(length = 10) // 4 chữ + 6 số = 10
    
    // Dùng @GeneratedValue và @GenericGenerator
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "destination_id_gen")
    @GenericGenerator(
        name = "destination_id_gen", // Tên của generator
        
        // **THAY ĐỔI QUAN TRỌNG**: 
        // Trỏ thẳng "strategy" đến LỚP JAVA của bạn
        strategy = "com.project.tour.StringPrefixedSequenceIdGenerator", 
        
        // Truyền tham số cho class Java
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.SEQUENCE_PARAM, value = "destination_seq"), // Tên sequence trong CSDL
            @Parameter(name = StringPrefixedSequenceIdGenerator.PREFIX_PARAM, value = "DEST"), // 4 chữ cái
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAM, value = "%06d") // 6 số
        }
    )
    private String destinationId;

    @Column(name = "destination_name", nullable = false)
    private String destinationName;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "entry_fee")
    private Double entryFee;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "touristDestinations")
    private Set<Tour> tours;
}