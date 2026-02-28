package com.PaymentService.PaymentServiceAPI.domain.repository;

import com.PaymentService.PaymentServiceAPI.domain.model.PaymentAudit;
import reactor.core.publisher.Mono;

public interface IPaymentAuditRepository {
    Mono<PaymentAudit> save(PaymentAudit audit);

    Mono<PaymentAudit> findByOrderId(Long orderId);
}
