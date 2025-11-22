package com.project.tour.Service;

import org.springframework.stereotype.Service;

import com.project.tour.Entity.Invoice;

import java.util.logging.Logger;

@Service
public class SmsService {

    private static final Logger LOGGER = Logger.getLogger(SmsService.class.getName());

    public void sendPaymentSuccessSms(Invoice invoice) {
        String tourDetails = "Tour: " + invoice.getTour().getTourName() +
                             ", Start: " + invoice.getTour().getTourStartDate();

        String message = "Dear " + invoice.getCustomerName() + ", " +
                         "your payment of " + invoice.getTotalAmount() + " for " + tourDetails + 
                         " was successful. Thank you for booking with us!";

        // Simulate sending SMS by logging the message
        LOGGER.info("Simulating SMS to " + invoice.getCustomerPhone() + ": " + message);
        
        // In a real-world application, you would integrate with an SMS gateway API here.
        // Example using a fictional smsGatewayClient:
        /*
        try {
            SmsRequest request = new SmsRequest(invoice.getCustomerPhone(), message);
            SmsResponse response = smsGatewayClient.send(request);
            if (!response.isSuccessful()) {
                LOGGER.warning("Failed to send SMS to " + invoice.getCustomerPhone() + ". Reason: " + response.getErrorMessage());
            }
        } catch (Exception e) {
            LOGGER.severe("Exception while sending SMS: " + e.getMessage());
        }
        */
    }
}
