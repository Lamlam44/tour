package com.project.tour.Service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

import com.project.tour.DTO.AccountRoleRequestDTO;
import com.project.tour.DTO.AccountRoleResponseDTO;
import com.project.tour.Entity.AccountRole;
import com.project.tour.Mapper.AccountRoleMapper;
import com.project.tour.Repository.AccountRoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountRoleService {

    private final AccountRoleRepository roleRepository;
    private final AccountRoleMapper accountRoleMapper;

    @Transactional
    public AccountRoleResponseDTO create(AccountRoleRequestDTO req) {
        // Kiểm tra nghiệp vụ bằng DTO
        roleRepository.findByRoleNameContainingIgnoreCase(req.getRoleName())
                .ifPresent(r -> { throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên vai trò đã tồn tại"); });

        // Dùng mapper để chuyển DTO -> Entity
        AccountRole roleEntity = accountRoleMapper.accountRoleRequestDTOToAccountRole(req);
        AccountRole saved = roleRepository.save(roleEntity);
        return accountRoleMapper.accountRoleToResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<AccountRoleResponseDTO> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(accountRoleMapper::accountRoleToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AccountRoleResponseDTO update(String id, AccountRoleRequestDTO req) {
        AccountRole existing = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy vai trò"));

        // Kiểm tra trùng tên (ngoại trừ chính nó)
        roleRepository.findByRoleNameContainingIgnoreCase(req.getRoleName())
                .filter(r -> !r.getAccountRoleId().equals(id))
                .ifPresent(r -> { throw new ResponseStatusException(HttpStatus.CONFLICT, "Tên vai trò đã tồn tại"); });

        // Dùng mapper để đổ dữ liệu DTO -> entity hiện có
        accountRoleMapper.updateAccountRoleFromDto(req, existing);
        AccountRole saved = roleRepository.save(existing);
        return accountRoleMapper.accountRoleToResponseDTO(saved);
    }
}
