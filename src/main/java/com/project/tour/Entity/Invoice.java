package com.project.tour.Entity;

import java.util.Set;
import java.time.LocalDateTime;

import com.project.tour.common.PaymentMethod;
import com.project.tour.common.PaymentStatus;
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
@Table(name = "invoices")
@SuppressWarnings("deprecation") // Tắt cảnh báo "deprecated"
public class Invoice {
    
    @Id
    @Column(length = 10) // 4 chữ + 6 số = 10
    
    // Dùng @GeneratedValue và @GenericGenerator
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_id_gen")
    @GenericGenerator(
        name = "invoice_id_gen", // Tên của generator
        
        // **THAY ĐỔI QUAN TRỌNG**: 
        // Trỏ thẳng "strategy" đến LỚP JAVA của bạn
        strategy = "com.project.tour.StringPrefixedSequenceIdGenerator", 
        
        // Truyền tham số cho class Java
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.SEQUENCE_PARAM, value = "invoice_seq"), // Tên sequence trong CSDL
            @Parameter(name = StringPrefixedSequenceIdGenerator.PREFIX_PARAM, value = "INVO"), // 4 chữ cái
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAM, value = "%06d") // 6 số
        }
    )
    private String invoiceId;

    @Column(name = "invoice_created_at", nullable = false)
    private LocalDateTime invoiceCreatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Column(nullable = false)
    private Integer numberOfPeople;

    @Column(name = "discount_amount", nullable = false)
    private Double discountAmount;

    @Column(name = "tax_amount", nullable = false)
    private Double taxAmount;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "customer_email")
    private String customerEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "account_id", nullable = true)
    private Account account;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "invoice_promotions",
        joinColumns = @JoinColumn(name = "invoice_id"),
        inverseJoinColumns = @JoinColumn(name = "promotion_id")
    )
    private Set<Promotion> promotions;

    @PrePersist
    protected void onCreate() {
        invoiceCreatedAt = java.time.LocalDateTime.now();
    }
}