package com.project.tour.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.project.tour.DTO.*;
import com.project.tour.Entity.*;
import com.project.tour.Mapper.InvoiceMapper;
import com.project.tour.Repository.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final TourRepository tourRepository;
    private final AccountRepository accountRepository;
    private final PromotionRepository promotionRepository;

    @Transactional
    public InvoiceResponseDTO create(InvoiceRequestDTO req) {
        Invoice invoiceEntity = invoiceMapper.invoiceRequestDTOToInvoice(req);
        Tour tour = tourRepository.findById(req.getTourId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour không tồn tại"));
        Account account = accountRepository.findById(req.getAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tài khoản không tồn tại"));
        Set<Promotion> promotionsSet = new HashSet<>();
        if (req.getPromotionIds() != null && !req.getPromotionIds().isEmpty()) {
            List<Promotion> promotionsList = promotionRepository.findAllById(req.getPromotionIds());

            if (promotionsList.size() != req.getPromotionIds().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Một hoặc nhiều mã khuyến mãi không tồn tại");
            }

            promotionsSet = new HashSet<>(promotionsList);
        }
        invoiceEntity.setTour(tour);
        invoiceEntity.setAccount(account);
        invoiceEntity.setPromotions(promotionsSet);
        Invoice saved = invoiceRepository.save(invoiceEntity);
        return invoiceMapper.invoiceToResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public InvoiceResponseDTO getById(String id) {
        Invoice inv = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hóa đơn không được tìm thấy"));
        InvoiceResponseDTO invoiceDTO = invoiceMapper.invoiceToResponseDTO(inv);
        return invoiceDTO;
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponseDTO> getAll() {
        return invoiceRepository.findAll().stream().map(invoiceMapper::invoiceToResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public InvoiceResponseDTO update(String id, InvoiceRequestDTO req) {
        Invoice existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy hóa đơn"));
    
        if (req.getStatus() == null || req.getStatus().isBlank()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Status không được để trống");
        }
        if (req.getTourId() == null || req.getTourId().isBlank()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tour không được để trống");
        }
        if (req.getAccountId() == null || req.getAccountId().isBlank()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account không được để trống");
        }
        if (req.getTotalAmount() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Total Amount không được để trống");
        }
        if (req.getPaymentMethod() == null || req.getPaymentMethod().isBlank()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Payment Method không được để trống");
        }
        if (req.getDiscountAmount() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Discount Amount không được để trống");
        }
        if (req.getTaxAmount() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tax Amount không được để trống");
        }
        if (req.getPromotionIds() != null && !req.getPromotionIds().isEmpty()) {
            List<Promotion> promotionsList = promotionRepository.findAllById(req.getPromotionIds());

            if (promotionsList.size() != req.getPromotionIds().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Một hoặc nhiều mã khuyến mãi không tồn tại");
            }
        }

        invoiceMapper.updateInvoiceFromDto(req, existingInvoice);
        Invoice saved = invoiceRepository.save(existingInvoice);
        return invoiceMapper.invoiceToResponseDTO(saved);
    }

    @Transactional
    public void delete(String id) {
        if (!invoiceRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy hóa đơn");
        }
        invoiceRepository.deleteById(id);
    }
}
