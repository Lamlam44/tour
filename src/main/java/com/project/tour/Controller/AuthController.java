package com.project.tour.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.project.tour.DTO.LoginRequestDTO;
import com.project.tour.DTO.LoginResponseDTO;
import com.project.tour.Entity.Account;
import com.project.tour.Repository.AccountRepository;
import com.project.tour.Service.OAuthVerificationService;
import com.project.tour.config.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final OAuthVerificationService oAuthVerificationService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO req) {
        // Tìm tài khoản theo username và load cả role
        Account account = accountRepository.findByUsernameWithRole(req.getUsername())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, 
                    "Tên đăng nhập hoặc mật khẩu không đúng"
                ));

        // Kiểm tra mật khẩu
        if (!passwordEncoder.matches(req.getPassword(), account.getPassword())) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, 
                "Tên đăng nhập hoặc mật khẩu không đúng"
            );
        }

        // Kiểm tra tài khoản có active không
        if (!account.isStatus()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, 
                "Tài khoản đã bị vô hiệu hóa"
            );
        }
        
        // Tạo JWT token
        String roleName = account.getRole() != null ? account.getRole().getRoleName() : "ROLE_USER";
        String token = jwtUtil.generateToken(account.getUsername(), roleName);

        // Tạo response với thông tin user
        LoginResponseDTO.UserInfoDTO userInfo = new LoginResponseDTO.UserInfoDTO(
            account.getAccountId(),
            account.getUsername(),
            roleName
        );

        LoginResponseDTO response = new LoginResponseDTO(token, userInfo);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // Trong trường hợp sử dụng JWT, có thể thêm token vào blacklist
        // Hiện tại chỉ return success
        return ResponseEntity.ok().build();
    }

    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        
        if (email == null || email.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email không được để trống");
        }

        // Kiểm tra xem tài khoản đã tồn tại chưa
        Account account = accountRepository.findByUsernameWithRole(email).orElse(null);
        
        if (account != null) {
            // Tài khoản đã tồn tại, đăng nhập luôn
            if (!account.isStatus()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tài khoản đã bị vô hiệu hóa");
            }

            String roleName = account.getRole() != null ? account.getRole().getRoleName() : "ROLE_USER";
            String token = jwtUtil.generateToken(account.getUsername(), roleName);
            
            LoginResponseDTO.UserInfoDTO userInfo = new LoginResponseDTO.UserInfoDTO(
                account.getAccountId(),
                account.getUsername(),
                roleName
            );

            LoginResponseDTO response = new LoginResponseDTO(token, userInfo);
            return ResponseEntity.ok(response);
        } else {
            // Tài khoản chưa tồn tại, tạo pending và gửi email xác thực
            oAuthVerificationService.createPendingForGoogle(email);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(Map.of("message", "Vui lòng kiểm tra email để xác thực tài khoản"));
        }
    }
}
