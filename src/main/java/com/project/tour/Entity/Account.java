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
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
@SuppressWarnings("deprecation") // Tắt cảnh báo "deprecated"
public class Account {
    
    @Id
    @Column(name = account_id, length = 10) 
    
    // Dùng @GeneratedValue và @GenericGenerator
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_gen")
    @GenericGenerator(
        name = "account_id_gen", // Tên của generator
        
        // **THAY ĐỔI QUAN TRỌNG**: 
        // Trỏ thẳng "strategy" đến LỚP JAVA của bạn
        strategy = "com.project.tour.StringPrefixedSequenceIdGenerator", 
        
        // Truyền tham số cho class Java
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.SEQUENCE_PARAM, value = "account_seq"), // Tên sequence trong CSDL
            @Parameter(name = StringPrefixedSequenceIdGenerator.PREFIX_PARAM, value = "ACCT"), // 3 chữ cái
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAM, value = "%06d") // 6 số
        }
    )
    private String accountId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "account_created_at", nullable = false)
    private LocalDateTime accountCreatedAt;

    @Column(name = "account_updated_at", nullable = false)
    private LocalDateTime accountUpdatedAt;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private AccountRole role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private Set<Invoice> invoices;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account")
    private Customer customer;

    @PrePersist
    protected void onCreate() {
        if (this.accountId == null) {
            generateId();
        }
        LocalDateTime now = LocalDateTime.now();
        accountCreatedAt = now;
        accountUpdatedAt = now;
    }

    public void generateId() {
        if (this.accountId == null) {
            this.accountId = "ACC-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
        }
    }
    @PreUpdate
    protected void onUpdate() {
        accountUpdatedAt = LocalDateTime.now();
    }
}