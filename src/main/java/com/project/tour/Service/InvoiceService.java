package com.project.tour.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.project.tour.Queue.EmailNotification;
import com.project.tour.Queue.NotificationProducer;
import com.project.tour.common.PaymentMethod;
import com.project.tour.common.PaymentStatus;
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
    private final NotificationProducer notificationProducer;

    // công cụ bắn tin nhắn WebSocket
    private final WebSocketNotificationService invoiceMessagingTemplate;

    @Transactional
    public InvoiceResponseDTO create(InvoiceRequestDTO req) {
        int updatedRows = tourRepository.decreaseSlots(req.getTourId(), req.getNumberOfPeople());
    
        if (updatedRows == 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Rất tiếc, tour này vừa hết chỗ hoặc không đủ vé cho bạn!");
        }

        Invoice invoiceEntity = invoiceMapper.invoiceRequestDTOToInvoice(req);
        Tour tour = tourRepository.findById(req.getTourId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour không tồn tại"));
        if (req.getAccountId() != null && !req.getAccountId().isEmpty()) {
            // 1. Nếu có tài khoản -> Tìm và set vào
            Account acc = accountRepository.findById(req.getAccountId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy Account"));
            invoiceEntity.setAccount(acc);
        } else {
            // 2. Nếu không có tài khoản (Khách vãng lai) -> Set null
            invoiceEntity.setAccount(null);
            
            // LƯU Ý: Lúc này bạn phải đảm bảo customerEmail, customerName 
            // trong Invoice đã được lấy từ request.getCustomerEmail()...
        }
        Set<Promotion> promotionsSet = new HashSet<>();
        if (req.getPromotionIds() != null && !req.getPromotionIds().isEmpty()) {
            List<Promotion> promotionsList = promotionRepository.findAllById(req.getPromotionIds());

            if (promotionsList.size() != req.getPromotionIds().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Một hoặc nhiều mã khuyến mãi không tồn tại");
            }

            promotionsSet = new HashSet<>(promotionsList);
        }
        invoiceEntity.setTour(tour);
        invoiceEntity.setPromotions(promotionsSet);
        invoiceEntity.setPaymentMethod(req.getPaymentMethod());
        invoiceEntity.setStatus(PaymentStatus.UNPAID); // Set initial status to UNPAID
        Invoice saved = invoiceRepository.save(invoiceEntity);

        // Gửi thông báo WebSocket đến Admin
        invoiceMessagingTemplate.sendInvoiceNotification(
            saved.getInvoiceId(),
            saved.getTotalAmount(),
            "Hóa đơn mới vừa được tạo!"
        );

        return invoiceMapper.invoiceToResponseDTO(saved);
    }

    @Transactional
    public void payInvoice(String invoiceId, PaymentMethod paymentMethod) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hóa đơn không được tìm thấy"));

        if (invoice.getStatus() == PaymentStatus.PAID) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Hóa đơn này đã được thanh toán.");
        }

        // Process payment
        invoice.setStatus(PaymentStatus.PAID);
        invoice.setPaymentMethod(paymentMethod); // Hardcode to CASH
        invoiceRepository.save(invoice);

        // Send notifications via RabbitMQ
        if (invoice.getCustomerEmail() != null && !invoice.getCustomerEmail().isEmpty()) {
            EmailNotification notification = new EmailNotification(
                invoice.getCustomerName(),
                invoice.getCustomerEmail(),
                invoice.getTour().getTourName(),
                invoice.getTour().getTourStartDate().toString(),
                invoice.getTour().getTourEndDate().toString(),
                invoice.getTotalAmount(),
                invoice.getPaymentMethod().toString()
            );
            notificationProducer.sendEmailNotification(notification);
        }
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
    
        if (req.getTourId() == null || req.getTourId().isBlank()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tour không được để trống");
        }
        if (req.getAccountId() == null || req.getAccountId().isBlank()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account không được để trống");
        }
        if (req.getTotalAmount() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Total Amount không được để trống");
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
