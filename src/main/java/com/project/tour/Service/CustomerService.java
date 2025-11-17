package com.project.tour.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.project.tour.DTO.CustomerRequestDTO;
import com.project.tour.DTO.CustomerResponseDTO;
import com.project.tour.Entity.Account;
import com.project.tour.Entity.Customer;
import com.project.tour.Mapper.CustomerMapper;
import com.project.tour.Repository.AccountRepository;
import com.project.tour.Repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final CustomerMapper customerMapper;

    @Transactional
    public CustomerResponseDTO create(CustomerRequestDTO req) {
        if (customerRepository.existsByCustomerEmail(req.getCustomerEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email đã tồn tại");
        }
        if (customerRepository.existsByCustomerPhone(req.getCustomerPhone())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Số điện thoại đã tồn tại");
        }

        Account account = accountRepository.findById(req.getAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tài khoản không được tìm thấy "));

        Customer customerEntity = customerMapper.customerRequestDTOToCustomer(req);
        customerEntity.setAccount(account);

        Customer saved = customerRepository.save(customerEntity);
        return customerMapper.customerToResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public CustomerResponseDTO getById(String id) {
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy khách hàng"));
        return customerMapper.customerToResponseDTO(c);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getAll() {
        return customerRepository.findAll().stream().map(customerMapper::customerToResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public CustomerResponseDTO update(String id, CustomerRequestDTO req) {
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy khách hàng"));

        if (req.getCustomerName() == null || req.getCustomerName().isBlank()) throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên khach hàng không được để trống");
        if (req.getCustomerEmail() == null || req.getCustomerEmail().isBlank()) throw new ResponseStatusException(HttpStatus.CONFLICT, "Email không được để trống"); ;
        if (req.getCustomerPhone() == null || req.getCustomerPhone().isBlank()) throw new ResponseStatusException(HttpStatus.CONFLICT, "Số điện thoại không được để trống");
        if (req.getCustomerAddress() == null || req.getCustomerAddress().isBlank()) throw new ResponseStatusException(HttpStatus.CONFLICT, "Địa chỉ không được để trống");
        if (req.getCustomerDateOfBirth() == null) throw new ResponseStatusException(HttpStatus.CONFLICT, "Ngày sinh không được để trống");
        if (req.getAccountId() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Mã tài khoản không được để trống");
        }

        Account account = accountRepository.findById(req.getAccountId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tài khoản không được tìm thấy"));
        
        customerMapper.updateCustomerFromDto(req, c);
        c.setAccount(account);
        Customer saved = customerRepository.save(c);
        return customerMapper.customerToResponseDTO(saved);
    }

    @Transactional
    public void delete(String id) {
        if (!customerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy khách hàng");
        }
        customerRepository.deleteById(id);
    }
}
