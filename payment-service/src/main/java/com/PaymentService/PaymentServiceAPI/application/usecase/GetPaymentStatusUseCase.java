package com.PaymentService.PaymentServiceAPI.application.usecase;

import com.PaymentService.PaymentServiceAPI.domain.model.PaymentAudit;
import com.PaymentService.PaymentServiceAPI.domain.repository.IPaymentAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetPaymentStatusUseCase {

    private final IPaymentAuditRepository repository;

    public Mono<PaymentAudit> execute(Long orderId) {
        return repository.findByOrderId(orderId);
    }

}
