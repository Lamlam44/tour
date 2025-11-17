package com.project.tour.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.project.tour.DTO.AccountRoleRequestDTO;
import com.project.tour.DTO.AccountRoleResponseDTO;
import com.project.tour.Service.AccountRoleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/roles")
@Validated
@RequiredArgsConstructor
public class AccountRoleController {

    private final AccountRoleService accountRoleService;

    @GetMapping
    public List<AccountRoleResponseDTO> getAll() {
        return accountRoleService.getAll();
    }

    @PostMapping
    public ResponseEntity<AccountRoleResponseDTO> create(@Valid @RequestBody AccountRoleRequestDTO req) {
        AccountRoleResponseDTO res = accountRoleService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/{id}")
    public AccountRoleResponseDTO update(@PathVariable String id, @Valid @RequestBody AccountRoleRequestDTO req) {
        return accountRoleService.update(id, req);
    }
}
