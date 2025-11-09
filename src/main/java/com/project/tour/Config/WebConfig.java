package com.project.tour.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        // Cấu hình này cho phép tất cả (hơi nới lỏng, dùng cho DEV)
        registry.addMapping("/**") // Áp dụng cho TẤT CẢ các đường dẫn
                .allowedOrigins("http://localhost:3000") // (!!!) Địa chỉ của React app
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}