package com.project.tour.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingRequestDTO {
    private String userEmail;
    private String bookingType;
    private Long associatedId;
    private LocalDateTime travelDate;
    private Double totalPrice;
    private String currency;
    // Add other fields relevant for creating a booking
}
