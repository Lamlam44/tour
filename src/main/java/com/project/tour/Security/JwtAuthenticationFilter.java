package com.project.tour.Security;

import com.project.tour.config.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        
        logger.info("Request URI: " + request.getRequestURI());
        logger.info("Authorization Header: " + requestTokenHeader);

        String username = null;
        String jwtToken = null;

        // JWT Token format: "Bearer token"
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtUtil.getUsernameFromToken(jwtToken);
                logger.info("Username from token: " + username);
            } catch (Exception e) {
                logger.warn("Unable to get JWT Token or token has expired: " + e.getMessage());
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        // Validate token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            logger.info("UserDetails loaded: " + userDetails.getUsername() + ", Authorities: " + userDetails.getAuthorities());

            if (jwtUtil.validateToken(jwtToken, userDetails.getUsername())) {
                
                logger.info("Token is valid, setting authentication");
                
                // Tạo authentication object
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails, 
                        null, 
                        userDetails.getAuthorities()
                    );
                    
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Set authentication vào SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.warn("Token validation failed");
            }
        }
        chain.doFilter(request, response);
    }
}
