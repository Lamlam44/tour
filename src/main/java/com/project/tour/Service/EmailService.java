package com.project.tour.Service;

import com.project.tour.Queue.EmailNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    @Value("${spring.mail.username:no-reply@example.com}")
    private String fromEmail;

    @Value("${app.mail.enabled:true}")
    private boolean mailEnabled;

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

    public void sendVerificationEmail(String toEmail, String token) {
        String verifyLink = baseUrl + "/accounts/verify?token=" + token;
        String subject = "Xác thực tài khoản";
        String text = "Chào bạn,\n\nVui lòng nhấn vào liên kết dưới đây để xác thực tài khoản của bạn:\n" +
                verifyLink + "\n\nLiên kết sẽ hết hạn sau 24 giờ.\n\nCảm ơn!";
        log.info("Sending account verification email to={} link={}", toEmail, verifyLink);
        send(toEmail, subject, text);
    }

    public void sendOAuthPendingEmail(String toEmail, String token, String provider) {
        String verifyLink = baseUrl + "/oauth/verify?token=" + token;
        String subject = "Xác thực đăng nhập " + provider;
        String text = "Bạn vừa chọn đăng nhập bằng " + provider + ".\n" +
                "Vui lòng xác thực email bằng cách truy cập liên kết sau:\n" + verifyLink + "\n\n" +
                "Liên kết sẽ hết hạn sau 24 giờ. Sau khi xác thực, tài khoản của bạn sẽ được tạo.\n" +
                "Nếu bạn không thực hiện yêu cầu này, hãy bỏ qua email.\n\nCảm ơn!";
        log.info("Sending OAuth pending email to={} provider={} link={}", toEmail, provider, verifyLink);
        send(toEmail, subject, text);
    }

    public void sendWelcomeEmail(String toEmail) {
        String subject = "Chào mừng bạn đến với Tour App";
        String text = "Chào mừng bạn đã xác thực thành công tài khoản. Chúc bạn có những trải nghiệm tuyệt vời!";
        log.info("Sending welcome email to={}", toEmail);
        send(toEmail, subject, text);
    }

    private void send(String to, String subject, String text) {
        if (!mailEnabled) {
            log.warn("Mail disabled by config. Skip sending to={} subject={}", to, subject);
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        log.info("Mail sent successfully to={} subject={}", to, subject);
    }
}
