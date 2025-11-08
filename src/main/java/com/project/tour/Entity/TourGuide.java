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
@Table(name = "tour_guides")
@SuppressWarnings("deprecation") // Tắt cảnh báo "deprecated"
public class TourGuide {
    
    @Id
    @Column(length = 10) // 4 chữ + 6 số = 10
    
    // Dùng @GeneratedValue và @GenericGenerator
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guide_id_gen")
    @GenericGenerator(
        name = "guide_id_gen", // Tên của generator
        
        // **THAY ĐỔI QUAN TRỌNG**: 
        // Trỏ thẳng "strategy" đến LỚP JAVA của bạn
        strategy = "com.project.tour.StringPrefixedSequenceIdGenerator", 
        
        // Truyền tham số cho class Java
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.SEQUENCE_PARAM, value = "guide_seq"), // Tên sequence trong CSDL
            @Parameter(name = StringPrefixedSequenceIdGenerator.PREFIX_PARAM, value = "GUID"), // 4 chữ cái
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAM, value = "%06d") // 6 số
        }
    )
    private String tourGuideId;

    @Column(name = "tour_guide_name", nullable = false)
    private String tourGuideName;

    @Column(name = "tour_guide_email", nullable = false)
    private String tourGuideEmail;

    @Column(name = "tour_guide_phone", nullable = false)
    private String tourGuidePhone;

    @Column(name = "tour_guide_experience_years", nullable = false)
    private int tourGuideExperienceYears;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tourGuide")
    private Set<Tour> tours;
}