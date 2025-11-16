    package com.project.tour.Entity;

    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;
    import lombok.NoArgsConstructor;

    // Thêm các import này
    import org.hibernate.annotations.GenericGenerator;
    import org.hibernate.annotations.Parameter;

    import com.project.tour.StringPrefixedSequenceIdGenerator;

    import java.time.*;

    @Getter
    @Setter
    @NoArgsConstructor
    @Entity
    @Table(name = "customers")
    @SuppressWarnings("deprecation") // Tắt cảnh báo "deprecated"
    public class Customer {
        
        @Id
        @Column(length = 10) // 4 chữ + 6 số = 10
        
        // Dùng @GeneratedValue và @GenericGenerator
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_gen")
        @GenericGenerator(
            name = "customer_id_gen", // Tên của generator
            
            // **THAY ĐỔI QUAN TRỌNG**: 
            // Trỏ thẳng "strategy" đến LỚP JAVA của bạn
            strategy = "com.project.tour.StringPrefixedSequenceIdGenerator", 
            
            // Truyền tham số cho class Java
            parameters = {
                @Parameter(name = StringPrefixedSequenceIdGenerator.SEQUENCE_PARAM, value = "customer_seq"), // Tên sequence trong CSDL
                @Parameter(name = StringPrefixedSequenceIdGenerator.PREFIX_PARAM, value = "CUST"), // 4 chữ cái
                @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAM, value = "%06d") // 6 số
            }
        )
        private String customerId;

        @Column(name = "customer_name", nullable = false)
        private String customerName;

        @Column(name = "customer_email", nullable = false, unique = true)
        private String customerEmail;

        @Column(name = "customer_phone", nullable = false, unique = true)
        private String customerPhone;

        @Column(name = "customer_address", nullable = false)
        private String customerAddress;

        @Column(name = "customer_date_of_birth", nullable = false)
        private LocalDate customerDateOfBirth;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "account_id")
        private Account account;
    }