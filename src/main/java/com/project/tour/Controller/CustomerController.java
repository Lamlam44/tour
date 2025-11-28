package com.project.tour.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.project.tour.DTO.CustomerRequestDTO;
import com.project.tour.DTO.CustomerResponseDTO;
import com.project.tour.Service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@Validated
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(@Valid @RequestBody CustomerRequestDTO req) {
        CustomerResponseDTO res = customerService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/{id}")
    public CustomerResponseDTO getById(@PathVariable String id) {
        return customerService.getById(id);
    }

    @GetMapping
    public List<CustomerResponseDTO> getAll() {
        return customerService.getAll();
    }

    @PutMapping("/{id}")
    public CustomerResponseDTO update(@PathVariable String id, @Valid @RequestBody CustomerRequestDTO req) {
        return customerService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        customerService.delete(id);
    }
}
