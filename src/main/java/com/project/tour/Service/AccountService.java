package com.project.tour.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.project.tour.DTO.*;
import com.project.tour.Entity.*;
import com.project.tour.Mapper.AccountMapper;
import com.project.tour.Mapper.AccountRoleMapper;
import com.project.tour.Repository.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountRoleRepository roleRepository;
    private final AccountMapper accountMapper;
    private final AccountRoleMapper roleMapper;

    @Transactional
    public AccountResponseDTO create(AccountRequestDTO req) {
        if (accountRepository.existsByUsername(req.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username đã tồn tại");
        }

        AccountRole role = roleRepository.findById(req.getRoleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role không tồn tại"));

        Account accountEntity = accountMapper.accountRequestDTOToAccount(req);
        accountEntity.setUsername(req.getUsername());
        accountEntity.setPassword(req.getPassword());
        accountEntity.setRole(role);

        Account saved = accountRepository.save(accountEntity);
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

    @Transactional(readOnly = true)
    public List<AccountRoleResponseDTO> findRolesByName(String name) {
        return roleRepository.findByRoleNameContainingIgnoreCase(name)
                .stream()
                .map(roleMapper::accountRoleToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AccountResponseDTO update(String id, AccountRequestDTO req) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy tài khoản"));
    
        if (req.getUsername() == null || req.getUsername().isBlank()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username không được để trống");
        }
        if (req.getPassword() == null || req.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Password không được để trống");
        }
        if (req.getRoleId() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role không được để trống");
        }
        AccountRole role = roleRepository.findById(req.getRoleId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy role"));

        accountMapper.updateAccountFromDto(req, existingAccount);
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
}
