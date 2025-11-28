package com.project.tour.Entity;

import java.util.Set;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.project.tour.StringPrefixedSequenceIdGenerator;

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
    @Column(length = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_id_gen")
    @GenericGenerator(
        name = "invoice_id_gen",
        strategy = "com.project.tour.StringPrefixedSequenceIdGenerator",
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.SEQUENCE_PARAM, value = "invoice_seq"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.PREFIX_PARAM, value = "INVO"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAM, value = "%06d")
        }
    )
    private String invoiceId;

    @Column(nullable = false)
    private LocalDateTime invoiceCreatedAt;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Double discountAmount;

    @Column(nullable = false)
    private Double taxAmount;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private String paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
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
        invoiceCreatedAt = LocalDateTime.now();
    }
}