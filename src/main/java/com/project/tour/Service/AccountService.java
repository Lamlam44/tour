package com.project.tour.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.project.tour.DTO.*;
import com.project.tour.Entity.*;
import com.project.tour.Repository.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountRoleRepository roleRepository;

    @Transactional
    public AccountResponseDTO create(AccountRequestDTO req) {
        if (accountRepository.existsByUsername(req.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        AccountRole role = roleRepository.findById(req.getRoleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));

        Account entity = new Account();
        entity.setUsername(req.getUsername());
        entity.setPassword(req.getPassword()); // TODO: hash password if needed
        entity.setRole(role);

        Account saved = accountRepository.save(entity);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public AccountResponseDTO getById(String id) {
        Account acc = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        return toResponse(acc);
    }

    @Transactional(readOnly = true)
    public List<AccountResponseDTO> getAll() {
        return accountRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public AccountResponseDTO update(String id, AccountRequestDTO req) {
        Account acc = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (req.getUsername() != null && !req.getUsername().isBlank()) {
            acc.setUsername(req.getUsername());
        }
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            acc.setPassword(req.getPassword()); // TODO: hash if needed
        }
        if (req.getRoleId() != null) {
            AccountRole role = roleRepository.findById(req.getRoleId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
            acc.setRole(role);
        }
        Account saved = accountRepository.save(acc);
        return toResponse(saved);
    }

    @Transactional
    public void delete(String id) {
        if (!accountRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        accountRepository.deleteById(id);
    }

    private AccountResponseDTO toResponse(Account entity) {
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setAccountId(entity.getAccountId());
        dto.setUsername(entity.getUsername());
        dto.setAccountCreatedAt(entity.getAccountCreatedAt());

        if (entity.getRole() != null) {
            AccountRoleResponseDTO rdto = new AccountRoleResponseDTO();
            rdto.setAccountRoleId(entity.getRole().getAccountRoleId());
            rdto.setRoleName(entity.getRole().getRoleName());
            dto.setRole(rdto);
        }
        return dto;
    }
}
