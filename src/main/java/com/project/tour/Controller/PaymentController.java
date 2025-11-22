package com.project.tour.Controller;

import com.project.tour.Service.InvoiceService;
import com.project.tour.Service.VNPayService;
import com.project.tour.common.PaymentMethod;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final InvoiceService invoiceService;
    private final VNPayService vnPayService;

    // ========================================================================
    // 1. THANHTOÁN THỦ CÔNG / GIẢ LẬP (Dùng để test nhanh hoặc Admin xác nhận)
    // ========================================================================
    @PostMapping("/invoices/{invoiceId}/pay")
    public ResponseEntity<String> payInvoice(@PathVariable String invoiceId) {
        invoiceService.payInvoice(invoiceId, PaymentMethod.CASH);
        return ResponseEntity.ok("Thanh toán hóa đơn thành công (Thủ công). Thông báo đã được gửi.");
    }

    // TẠO LINK THANH TOÁN VNPAY 
    @PostMapping("/vnpay/create-url")
    public ResponseEntity<String> createVNPayUrl(@RequestBody Map<String, Object> request) {
        // 1. Lấy invoiceId từ JSON Body (QUAN TRỌNG)
        String invoiceId = String.valueOf(request.get("invoiceId"));
        
        // 2. Lấy số tiền
        long amount = Long.parseLong(String.valueOf(request.get("amount")).replace(".0", ""));
        String orderInfo = "Thanh toan don hang " + invoiceId;
        
        // Link trả về khi thanh toán xong
        String returnUrl = "http://localhost:8080/api/payments/vnpay-return"; 
        
        // 3. Gọi Service với đầy đủ tham số
        String paymentUrl = vnPayService.createPaymentUrl(invoiceId, amount, orderInfo, returnUrl);
        
        return ResponseEntity.ok(paymentUrl);
    }

    // XỬ LÝ KẾT QUẢ VNPAY TRẢ VỀ (Redirect URL)
    @GetMapping("/vnpay-return")
    public ResponseEntity<String> vnPayReturn(@RequestParam Map<String, String> params) {
        String vnp_ResponseCode = params.get("vnp_ResponseCode");
        
        // Lấy mã giao dịch (Đang có dạng: INV-001_1705558888)
        String vnp_TxnRef = params.get("vnp_TxnRef");
        
        // --- SỬA ĐỔI QUAN TRỌNG: TÁCH CHUỖI ĐỂ LẤY ID GỐC ---
        String invoiceId = "";
        if (vnp_TxnRef != null && vnp_TxnRef.contains("_")) {
            // Cắt bỏ phần đuôi thời gian, chỉ lấy phần đầu (invoiceId)
            String[] parts = vnp_TxnRef.split("_");
            invoiceId = parts[0]; 
        } else {
            invoiceId = vnp_TxnRef; // Dự phòng nếu không có đuôi
        }
        // ----------------------------------------------------

        if ("00".equals(vnp_ResponseCode)) {
            // Giao dịch thành công -> Cập nhật Database với ID gốc
            invoiceService.payInvoice(invoiceId, PaymentMethod.VNPAY);
            
            return ResponseEntity.ok("Thanh toán VNPay thành công! Đơn hàng " + invoiceId + " đã được cập nhật.");
        } else {
            return ResponseEntity.badRequest().body("Thanh toán thất bại. Mã lỗi: " + vnp_ResponseCode);
        }
    }
}