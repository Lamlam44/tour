package com.project.tour.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.temporal.ChronoUnit;

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

// ===========================
// TÍNH TOTAL, DISCOUNT VÀ TAX
// ===========================
private double calculateDiscount(Tour tour, Set<Promotion> promotions) {
    // Tổng giảm giá theo % từ promotions
    return promotions.stream()
            .mapToDouble(p -> tour.getTourPrice() * p.getDiscountPercentage() / 100.0)
            .sum();
}

private double calculateTotal(Tour tour, double discountAmount, double taxRate) {
    double tourPrice = tour.getTourPrice();

    Accommodation acc = tour.getAccommodation();
    double accommodationCost = 0;
    if (acc != null) {
        long days = ChronoUnit.DAYS.between(tour.getTourStartDate(), tour.getTourEndDate());
        if (days <= 0) days = 1;
        accommodationCost = acc.getPricePerNight() * days;
    }

    double originalPrice = tourPrice + accommodationCost;
    double taxAmount = originalPrice * taxRate;
    double total = originalPrice - discountAmount + taxAmount;
    return Math.max(total, 0);
}

// ===========================
// CREATE INVOICE
// ===========================
@Transactional
public InvoiceResponseDTO create(InvoiceRequestDTO req) {

    Invoice invoiceEntity = invoiceMapper.invoiceRequestDTOToInvoice(req);

    Tour tour = tourRepository.findById(req.getTourId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour không tồn tại"));

    Account account = accountRepository.findById(req.getAccountId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tài khoản không tồn tại"));

    final Set<Promotion> promotionsSet;
    if (req.getPromotionIds() != null && !req.getPromotionIds().isEmpty()) {
        List<Promotion> promotionsList = promotionRepository.findAllById(req.getPromotionIds());
        if (promotionsList.size() != req.getPromotionIds().size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Một hoặc nhiều mã khuyến mãi không tồn tại");
        }
        promotionsSet = new HashSet<>(promotionsList);
    } else {
        promotionsSet = new HashSet<>();
    }

    // Tính discount tự động
    double discountAmount = calculateDiscount(tour, promotionsSet);
    invoiceEntity.setDiscountAmount(discountAmount);

    // Thuế 5%
    double taxAmount = (tour.getTourPrice() + (tour.getAccommodation() != null ? tour.getAccommodation().getPricePerNight() * Math.max(ChronoUnit.DAYS.between(tour.getTourStartDate(), tour.getTourEndDate()), 1) : 0)) * 0.05;
    invoiceEntity.setTaxAmount(taxAmount);

    double totalAmount = calculateTotal(tour, discountAmount, 0.05);
    invoiceEntity.setTotalAmount(totalAmount);

    invoiceEntity.setTour(tour);
    invoiceEntity.setAccount(account);
    invoiceEntity.setPromotions(promotionsSet);

    Invoice saved = invoiceRepository.save(invoiceEntity);
    return invoiceMapper.invoiceToResponseDTO(saved);
}

// ===========================
// GET BY ID
// ===========================
@Transactional(readOnly = true)
public InvoiceResponseDTO getById(String id) {
    Invoice inv = invoiceRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hóa đơn không được tìm thấy"));
    return invoiceMapper.invoiceToResponseDTO(inv);
}

// ===========================
// GET ALL
// ===========================
@Transactional(readOnly = true)
public List<InvoiceResponseDTO> getAll() {
    return invoiceRepository.findAll()
            .stream()
            .map(invoiceMapper::invoiceToResponseDTO)
            .collect(Collectors.toList());
}

// ===========================
// UPDATE
// ===========================
@Transactional
public InvoiceResponseDTO update(String id, InvoiceRequestDTO req) {

    Invoice existingInvoice = invoiceRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy hóa đơn"));

    Tour tour = tourRepository.findById(req.getTourId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour không tồn tại"));

    Account account = accountRepository.findById(req.getAccountId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tài khoản không tồn tại"));

    final Set<Promotion> promotionsSet;
    if (req.getPromotionIds() != null && !req.getPromotionIds().isEmpty()) {
        List<Promotion> promotionsList = promotionRepository.findAllById(req.getPromotionIds());
        if (promotionsList.size() != req.getPromotionIds().size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Một hoặc nhiều mã khuyến mãi không tồn tại");
        }
        promotionsSet = new HashSet<>(promotionsList);
    } else {
        promotionsSet = new HashSet<>();
    }

    invoiceMapper.updateInvoiceFromDto(req, existingInvoice);

    existingInvoice.setTour(tour);
    existingInvoice.setAccount(account);
    existingInvoice.setPromotions(promotionsSet);

    double discountAmount = calculateDiscount(tour, promotionsSet);
    existingInvoice.setDiscountAmount(discountAmount);

    double taxAmount = (tour.getTourPrice() + (tour.getAccommodation() != null ? tour.getAccommodation().getPricePerNight() * Math.max(ChronoUnit.DAYS.between(tour.getTourStartDate(), tour.getTourEndDate()), 1) : 0)) * 0.05;
    existingInvoice.setTaxAmount(taxAmount);

    double totalAmount = calculateTotal(tour, discountAmount, 0.05);
    existingInvoice.setTotalAmount(totalAmount);

    Invoice saved = invoiceRepository.save(existingInvoice);
    return invoiceMapper.invoiceToResponseDTO(saved);
}

// ===========================
// DELETE
// ===========================
@Transactional
public void delete(String id) {
    if (!invoiceRepository.existsById(id)) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy hóa đơn");
    }
    invoiceRepository.deleteById(id);
}

}
