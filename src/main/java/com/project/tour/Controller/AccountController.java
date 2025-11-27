package com.project.tour.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.project.tour.DTO.AccountRequestDTO;
import com.project.tour.DTO.AccountResponseDTO;
import com.project.tour.DTO.AccountRoleResponseDTO;
import com.project.tour.Service.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@Validated
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> create(@Valid @RequestBody AccountRequestDTO req) {
        AccountResponseDTO res = accountService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/{id}")
    public AccountResponseDTO getById(@PathVariable String id) {
        return accountService.getById(id);
    }

    @GetMapping
    public List<AccountResponseDTO> getAll() {
        return accountService.getAll();
    }

    @GetMapping("/roles/search")
    public List<AccountRoleResponseDTO> searchRoles(@RequestParam String name) {
        return accountService.findRolesByName(name);
    }

    @PutMapping("/{id}")
    public AccountResponseDTO update(@PathVariable String id, @Valid @RequestBody AccountRequestDTO req) {
        return accountService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        accountService.delete(id);
    }

    @GetMapping("/verify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verify(@RequestParam("token") String token) {
        accountService.verifyAccount(token);
    }
}
