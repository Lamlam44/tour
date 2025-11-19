package com.project.tour.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.project.tour.DTO.InvoiceRequestDTO;
import com.project.tour.DTO.InvoiceResponseDTO;
import com.project.tour.Service.InvoiceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/invoices")
@Validated
@RequiredArgsConstructor
public class InvoiceController {

	private final InvoiceService invoiceService;

	@PostMapping
	public ResponseEntity<InvoiceResponseDTO> create(@Valid @RequestBody InvoiceRequestDTO req) {
		InvoiceResponseDTO res = invoiceService.create(req);
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}

	@GetMapping("/{id}")
	public InvoiceResponseDTO getById(@PathVariable String id) {
		return invoiceService.getById(id);
	}

	@GetMapping
	public List<InvoiceResponseDTO> getAll() {
		return invoiceService.getAll();
	}

	@PutMapping("/{id}")
	public InvoiceResponseDTO update(@PathVariable String id, @Valid @RequestBody InvoiceRequestDTO req) {
		return invoiceService.update(id, req);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String id) {
		invoiceService.delete(id);
	}
}
