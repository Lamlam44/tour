package com.project.tour.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.tour.DTO.*;
import com.project.tour.Entity.*;
import com.project.tour.Mapper.AccountMapper;
import com.project.tour.Repository.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountRoleRepository roleRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Transactional
    public AccountResponseDTO create(AccountRequestDTO req) {
        // Kiểm tra nghiệp vụ bằng DTO
        if (accountRepository.existsByUsername(req.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên đăng nhập đã tồn tại");
        }

        AccountRole role = roleRepository.findById(req.getRoleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy vai trò"));

        Account accountEntity = accountMapper.accountRequestDTOToAccount(req);
        accountEntity.setUsername(req.getUsername());
        accountEntity.setPassword(passwordEncoder.encode(req.getPassword())); // mã hóa mật khẩu
        accountEntity.setRole(role);
        // Khởi tạo token xác thực email
        String token = UUID.randomUUID().toString();
        accountEntity.setVerificationToken(token);
        accountEntity.setVerificationTokenExpiry(LocalDateTime.now().plusHours(24));
        accountEntity.setEmailVerified(false);

        Account saved = accountRepository.save(accountEntity);
        // Gửi email xác thực (username giả định là email)
        try {
            emailService.sendVerificationEmail(saved.getUsername(), token);
        } catch (Exception ignored) {}
        return accountMapper.accountToResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public AccountResponseDTO getById(String id) {
        Account acc = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tài khoản không được tìm thấy"));
        AccountResponseDTO accountDTO = accountMapper.accountToResponseDTO(acc);
        return accountDTO;
    }

    @Transactional(readOnly = true)
    public List<AccountResponseDTO> getAll() {
        return accountRepository.findAll().stream().map(accountMapper::accountToResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public AccountResponseDTO update(String id, AccountRequestDTO req) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy tài khoản"));
    
        // Kiểm tra nghiệp vụ bằng DTO
        if (req.getUsername() == null || req.getUsername().isBlank()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên đăng nhập không được để trống");
        }
        if (req.getPassword() == null || req.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Mật khẩu không được để trống");
        }
        if (req.getRoleId() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Vai trò không được để trống");
        }

        // Không trùng tên đăng nhập với tài khoản khác
        accountRepository.findByUsername(req.getUsername())
                .filter(acc -> !acc.getAccountId().equals(id))
                .ifPresent(acc -> { throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên đăng nhập đã tồn tại"); });

        AccountRole role = roleRepository.findById(req.getRoleId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy vai trò"));

        accountMapper.updateAccountFromDto(req, existingAccount);
        // Mã hóa mật khẩu khi cập nhật
        existingAccount.setPassword(passwordEncoder.encode(req.getPassword()));
        existingAccount.setRole(role);
        Account saved = accountRepository.save(existingAccount);
        return accountMapper.accountToResponseDTO(saved);
    }

    @Transactional
    public void delete(String id) {
        if (!accountRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy tài khoản");
        }
        accountRepository.deleteById(id);
    }

    @Transactional
    public void verifyAccount(String token) {
        Account acc = accountRepository.findByVerificationToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token không hợp lệ"));
        if (acc.isEmailVerified()) {
            return;
        }
        if (acc.getVerificationTokenExpiry() == null || acc.getVerificationTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token đã hết hạn");
        }
        acc.setEmailVerified(true);
        acc.setVerificationToken(null);
        acc.setVerificationTokenExpiry(null);
        accountRepository.save(acc);
        try {
            emailService.sendWelcomeEmail(acc.getUsername());
        } catch (Exception ignored) {}
    }
}
