package com.project.tour.Queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.project.tour.Config.RabbitMQConfig;
import com.project.tour.Service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void receiveEmailNotification(EmailNotification notification) {
        emailService.sendPaymentSuccessEmail(notification);
    }
}
