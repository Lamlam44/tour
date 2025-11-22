package com.project.tour.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    // Hàm 1: Bắn tin đơn hàng (Invoice)
    public void sendInvoiceNotification(String invoiceId, double amount, String message) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", invoiceId);
        data.put("amount", amount);
        data.put("message", message);
        data.put("timestamp", System.currentTimeMillis());
        
        // Gửi đến kênh: /topic/admin/invoices
        messagingTemplate.convertAndSend("/topic/admin/invoices", data);
    }

    // Hàm 2: Bắn tin cập nhật Tour (Khi có người đặt vé)
    public void sendTourUpdate(String tourId, int remainingSlots) {
        Map<String, Object> data = new HashMap<>();
        data.put("tourId", tourId);
        data.put("remainingSlots", remainingSlots);
        data.put("type", "UPDATE_SLOT");
        
        // Gửi đến kênh: /topic/admin/tours
        messagingTemplate.convertAndSend("/topic/admin/tours", data);
    }

    // Hàm 3: Bắn tin có User mới đăng ký
    public void sendNewUserAlert(String email, String name) {
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("name", name);
        data.put("message", "Thành viên mới vừa tham gia!");
        
        // Gửi đến kênh: /topic/admin/users
        messagingTemplate.convertAndSend("/topic/admin/users", data);
    }
}