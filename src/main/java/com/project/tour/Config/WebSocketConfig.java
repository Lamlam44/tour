package com.project.tour.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @SuppressWarnings("null")
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 1. Định nghĩa nơi Client (Admin) sẽ đăng ký nhận tin (Subscribe)
        // Admin sẽ lắng nghe các đường dẫn bắt đầu bằng "/topic"
        config.enableSimpleBroker("/topic");

        // 2. Tiền tố cho các tin nhắn từ Client gửi lên (nếu có chat)
        config.setApplicationDestinationPrefixes("/app");
    }

    @SuppressWarnings("null")
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 3. Đường dẫn chính để kết nối WebSocket (Handshake URL)
        // React sẽ kết nối vào: http://localhost:8080/ws
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // Quan trọng: Cho phép mọi nguồn (để React/Postman gọi được)
                .withSockJS(); // Hỗ trợ dự phòng nếu trình duyệt không có WebSocket
    }
}