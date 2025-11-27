package com.project.tour.Service;

import com.project.tour.Entity.*;
import com.project.tour.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthVerificationService {

    private final OAuthPendingAccountRepository pendingRepo;
    private final AccountRepository accountRepository;
    private final AccountRoleRepository roleRepository;
    private final EmailService emailService;

    @Transactional
    public void createPendingForGoogle(String email) {
        // Nếu account đã tồn tại -> không tạo pending
        if (accountRepository.existsByUsername(email)) {
            // Có thể là user đã verify từ trước, ghi log
            System.out.println("[OAuthVerification] Account already exists for email=" + email);
            return;
        }
        AccountRole role = roleRepository.findByRoleNameContainingIgnoreCase("ROLE_USER")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ROLE_USER not found"));

        OAuthPendingAccount pending = new OAuthPendingAccount();
        pending.setEmail(email);
        pending.setProvider("google");
        pending.setRole(role);
        String token = UUID.randomUUID().toString();
        pending.setToken(token);
        pending.setTokenExpiry(LocalDateTime.now().plusHours(24));

        pendingRepo.save(pending);
        System.out.println("[OAuthVerification] Pending created email=" + email + " token=" + token);
        try {
            emailService.sendOAuthPendingEmail(email, token, "Google");
        } catch (Exception ex) {
            System.err.println("[OAuthVerification] Failed to send pending email: " + ex.getMessage());
        }
    }

    @Transactional
    public void verify(String token) {
        OAuthPendingAccount pending = pendingRepo.findByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token không hợp lệ"));

        if (pending.getTokenExpiry() == null || pending.getTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token đã hết hạn");
        }

        // Nếu tài khoản đã tồn tại (đăng ký trước khi verify) thì thôi, chỉ gửi welcome
        if (!accountRepository.existsByUsername(pending.getEmail())) {
            Account acc = new Account();
            acc.setUsername(pending.getEmail());
            acc.setPassword("OAUTH2");
            acc.setRole(pending.getRole());
            acc.setEmailVerified(true);
            accountRepository.save(acc);
        }

        // Xóa pending record
        pendingRepo.delete(pending);
        System.out.println("[OAuthVerification] Verified token=" + token + " creating/sending welcome for=" + pending.getEmail());
        try {
            emailService.sendWelcomeEmail(pending.getEmail());
        } catch (Exception ex) {
            System.err.println("[OAuthVerification] Failed to send welcome email: " + ex.getMessage());
        }
    }

    @Transactional
    public void resend(String email) {
        OAuthPendingAccount pending = pendingRepo.findByEmailAndProvider(email, "google")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy yêu cầu xác thực đang chờ"));
        String newToken = UUID.randomUUID().toString();
        pending.setToken(newToken);
        pending.setTokenExpiry(LocalDateTime.now().plusHours(24));
        pendingRepo.save(pending);
        System.out.println("[OAuthVerification] Resend token email=" + email + " token=" + newToken);
        try {
            emailService.sendOAuthPendingEmail(email, newToken, "Google");
        } catch (Exception ex) {
            System.err.println("[OAuthVerification] Failed to resend email: " + ex.getMessage());
        }
    }
}
