package com.project.tour.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.tour.Security.CustomUserDetailsService;
import com.project.tour.Security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
        CustomUserDetailsService customUserDetailService,
        JwtAuthenticationFilter jwtAuthenticationFilter
    ) {
        this.customUserDetailService = customUserDetailService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configure(http)) // Enable CORS
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/oauth/**").permitAll()
                // Cho phép POST để đăng ký account và customer mới
                .requestMatchers("POST", "/api/accounts").permitAll()
                .requestMatchers("POST", "/api/customers").permitAll()
                // Các request khác của accounts và customers cần ADMIN
                .requestMatchers("/api/accounts/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/customers/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/account-roles/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll()
            )
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable());
            
        // Thêm JWT filter trước UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder);
        return builder.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
