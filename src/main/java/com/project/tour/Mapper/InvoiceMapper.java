package com.project.tour.Mapper;

import com.project.tour.DTO.InvoiceRequestDTO;
import com.project.tour.DTO.InvoiceResponseDTO;
import com.project.tour.Entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper này chuyển đổi giữa Invoice (Entity) và các DTO của nó.
 * Chúng ta giả định rằng bạn CŨNG SẼ TẠO:
 * - TourMapper
 * - AccountMapper
 * - PromotionMapper
 */
@Mapper(
    componentModel = "spring",
    uses = {
        TourMapper.class,       // Cần để map 'tour'
        AccountMapper.class,    // Cần để map 'account'
        PromotionMapper.class   // Cần để map 'promotions' sang 'appliedPromotions'
    }
)
public interface InvoiceMapper {

    /**
     * Chuyển đổi từ Invoice (Entity) sang InvoiceResponseDTO.
     * Dùng khi trả dữ liệu về cho client.
     */
    // Cần mapping này vì tên trường không khớp:
    // Entity là "promotions", DTO là "appliedPromotions"
    @Mapping(source = "promotions", target = "appliedPromotions")
    InvoiceResponseDTO invoiceToResponseDTO(Invoice invoice);

    /**
     * Chuyển đổi từ InvoiceRequestDTO sang Invoice (Entity).
     * Dùng khi tạo mới một Hóa đơn.
     */
    // Bỏ qua tất cả các trường sẽ được xử lý ở Service hoặc
    // được gán tự động (như @PrePersist)
    @Mapping(target = "invoiceId", ignore = true) // Sẽ được tạo ở Service
    @Mapping(target = "invoiceCreatedAt", ignore = true) // Sẽ được gán bằng @PrePersist
    @Mapping(target = "tour", ignore = true) // Service sẽ xử lý từ tourId
    @Mapping(target = "account", ignore = true) // Service sẽ xử lý từ accountId
    @Mapping(target = "promotions", ignore = true) // Service sẽ xử lý từ promotionIds
    Invoice invoiceRequestDTOToInvoice(InvoiceRequestDTO requestDTO);
}