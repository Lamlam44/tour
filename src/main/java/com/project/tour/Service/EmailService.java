package com.project.tour.Service;

import com.project.tour.Queue.EmailNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPaymentSuccessEmail(EmailNotification notification) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notification.getCustomerEmail());
            message.setSubject("Payment Successful for Your Tour Booking");

            String tourDetails = "Tour: " + notification.getTourName() +
                                 "\nStart Date: " + notification.getTourStartDate() +
                                 "\nEnd Date: " + notification.getTourEndDate();

            String paymentDetails = "Total Amount: " + notification.getTotalAmount() +
                                    "\nPayment Method: " + notification.getPaymentMethod();

            message.setText("Dear " + notification.getCustomerName() + "\n\n" +
                            "Your payment has been successfully processed for the following tour:\n\n" +
                            tourDetails + "\n\n" +
                            "Payment Details:\n" +
                            paymentDetails + "\n\n" + 
                            "Thank you for booking with us!\n\n" +
                            "Best regards,\n" +
                            "Tour Project Team");

            mailSender.send(message);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            // In a real application, you might want to handle this more gracefully
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}
