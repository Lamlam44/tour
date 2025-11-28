package com.project.tour.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        // Cấu hình này cho phép tất cả (hơi nới lỏng, dùng cho DEV)
        registry.addMapping("/**") // Áp dụng cho TẤT CẢ các đường dẫn
                .allowedOrigins("http://localhost:3000", "https://accounts.google.com") // Thêm Google
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public Filter coopFilter() {
        return new Filter() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                
                // Thêm headers cho Google Sign-In compatibility
                httpResponse.setHeader("Cross-Origin-Opener-Policy", "same-origin-allow-popups");
                httpResponse.setHeader("Cross-Origin-Embedder-Policy", "unsafe-none");
                
                chain.doFilter(request, response);
            }
        };
    }
}
