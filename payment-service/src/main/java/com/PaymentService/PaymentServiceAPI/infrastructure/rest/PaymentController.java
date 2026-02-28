package com.PaymentService.PaymentServiceAPI.infrastructure.rest;

import com.PaymentService.PaymentServiceAPI.application.usecase.GetPaymentStatusUseCase;
import com.PaymentService.PaymentServiceAPI.application.usecase.RetryPaymentUseCase;
import com.PaymentService.PaymentServiceAPI.domain.model.PaymentAudit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final GetPaymentStatusUseCase getStatusUseCase;
    private final RetryPaymentUseCase retryUseCase;

    @GetMapping("/{orderId}")
    public Mono<ResponseEntity<PaymentAudit>> getStatus(@PathVariable Long orderId) {
        return getStatusUseCase.execute(orderId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/{orderId}/retry")
    public Mono<ResponseEntity<Void>> retry(@PathVariable Long orderId) {
        return retryUseCase.execute(orderId)
                .then(Mono.just(ResponseEntity.accepted().build()));
    }
}
