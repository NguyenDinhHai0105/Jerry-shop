package org.jerry.payment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jerry.payment.dto.PaymentRequest;
import org.jerry.payment.dto.PaymentResponse;
import org.jerry.payment.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.createPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> updatePayment(
            @PathVariable UUID id,
            @Valid @RequestBody PaymentRequest request
    ) {
        PaymentResponse response = paymentService.updatePayment(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable UUID id) {
        PaymentResponse response = paymentService.getPaymentById(id);
        return ResponseEntity.ok(response);
    }
}
