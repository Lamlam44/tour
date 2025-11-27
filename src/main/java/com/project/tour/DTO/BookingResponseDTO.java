package com.project.tour.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingResponseDTO {
    private Long id;
    private String bookingCode;
    private String userEmail;
    private String bookingType;
    private Long associatedId;
    private LocalDateTime bookingDate;
    private LocalDateTime travelDate;
    private Double totalPrice;
    private String currency;
    private String paymentStatus;
    private String bookingStatus;
    // Add other fields relevant for displaying booking information
}
