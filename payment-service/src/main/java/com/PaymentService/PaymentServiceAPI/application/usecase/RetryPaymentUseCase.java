package com.PaymentService.PaymentServiceAPI.application.usecase;

import com.PaymentService.PaymentServiceAPI.domain.repository.IPaymentAuditRepository;
import com.PaymentService.PaymentServiceAPI.infrastructure.messaging.PaymentPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RetryPaymentUseCase {

    private final IPaymentAuditRepository repository;
    private final PaymentPublisher publisher;

    public Mono<Void> execute(Long orderId) {
        return repository.findByOrderId(orderId)
                .flatMap(audit -> {
                    boolean isSuccess = "APPROVED".equals(audit.getStatus());
                    publisher.publishPaymentResponse(orderId, isSuccess);
                    return Mono.empty();
                }).then();
    }
}
