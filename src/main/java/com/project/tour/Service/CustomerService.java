package com.project.tour.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.project.tour.DTO.AccountResponseDTO;
import com.project.tour.DTO.CustomerRequestDTO;
import com.project.tour.DTO.CustomerResponseDTO;
import com.project.tour.Entity.Account;
import com.project.tour.Entity.Customer;
import com.project.tour.Repository.AccountRepository;
import com.project.tour.Repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public CustomerResponseDTO create(CustomerRequestDTO req) {
        if (customerRepository.existsByCustomerEmail(req.getCustomerEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        if (customerRepository.existsByCustomerPhone(req.getCustomerPhone())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone already exists");
        }

        Account account = accountRepository.findById(req.getAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        Customer entity = new Customer();
        entity.setCustomerName(req.getCustomerName());
        entity.setCustomerEmail(req.getCustomerEmail());
        entity.setCustomerPhone(req.getCustomerPhone());
        entity.setCustomerAddress(req.getCustomerAddress());
        entity.setCustomerDateOfBirth(req.getCustomerDateOfBirth());
        entity.setAccount(account);

        Customer saved = customerRepository.save(entity);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public CustomerResponseDTO getById(String id) {
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        return toResponse(c);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getAll() {
        return customerRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public CustomerResponseDTO update(String id, CustomerRequestDTO req) {
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        if (req.getCustomerName() != null && !req.getCustomerName().isBlank()) c.setCustomerName(req.getCustomerName());
        if (req.getCustomerEmail() != null && !req.getCustomerEmail().isBlank()) c.setCustomerEmail(req.getCustomerEmail());
        if (req.getCustomerPhone() != null && !req.getCustomerPhone().isBlank()) c.setCustomerPhone(req.getCustomerPhone());
        if (req.getCustomerAddress() != null && !req.getCustomerAddress().isBlank()) c.setCustomerAddress(req.getCustomerAddress());
        if (req.getCustomerDateOfBirth() != null) c.setCustomerDateOfBirth(req.getCustomerDateOfBirth());
        if (req.getAccountId() != null) {
            Account account = accountRepository.findById(req.getAccountId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
            c.setAccount(account);
        }

        Customer saved = customerRepository.save(c);
        return toResponse(saved);
    }

    @Transactional
    public void delete(String id) {
        if (!customerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        customerRepository.deleteById(id);
    }

    private CustomerResponseDTO toResponse(Customer entity) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        // CustomerResponseDTO.customerId is Long but entity uses String; try to parse
        try {
            if (entity.getCustomerId() != null) {
                dto.setCustomerId(Long.valueOf(entity.getCustomerId()));
            }
        } catch (NumberFormatException ignored) {
            // leave null if not numeric
        }
        dto.setCustomerName(entity.getCustomerName());
        dto.setCustomerEmail(entity.getCustomerEmail());
        dto.setCustomerPhone(entity.getCustomerPhone());
        dto.setCustomerAddress(entity.getCustomerAddress());
        dto.setCustomerDateOfBirth(entity.getCustomerDateOfBirth());

        if (entity.getAccount() != null) {
            AccountResponseDTO adto = new AccountResponseDTO();
            adto.setAccountId(entity.getAccount().getAccountId());
            adto.setUsername(entity.getAccount().getUsername());
            adto.setAccountCreatedAt(entity.getAccount().getAccountCreatedAt());
            dto.setAccount(adto);
        }
        return dto;
    }
}
