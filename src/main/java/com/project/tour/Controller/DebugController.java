package com.project.tour.Controller;

import com.project.tour.Repository.OAuthPendingAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/debug")
@RequiredArgsConstructor
public class DebugController {

    private final OAuthPendingAccountRepository pendingRepo;

    @Value("${app.mail.enabled:true}")
    private boolean mailEnabled;

    // Chỉ dành cho DEV: lấy token đang chờ theo email khi mail bị tắt
    @GetMapping("/pending-token")
    public ResponseEntity<?> getPendingToken(@RequestParam("email") String email) {
        if (mailEnabled) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Endpoint chỉ dùng khi app.mail.enabled=false"));
        }
        return pendingRepo.findByEmailAndProvider(email, "google")
                .<ResponseEntity<?>>map(p -> ResponseEntity.ok(Map.of(
                        "email", p.getEmail(),
                        "provider", p.getProvider(),
                        "token", p.getToken(),
                        "expiry", String.valueOf(p.getTokenExpiry())
                )))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Không tìm thấy pending cho email này")));
    }
}
