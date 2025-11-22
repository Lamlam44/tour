package com.project.tour.Queue;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotification implements Serializable {
    private String customerName;
    private String customerEmail;
    private String tourName;
    private String tourStartDate;
    private String tourEndDate;
    private Double totalAmount;
    private String paymentMethod;
}
