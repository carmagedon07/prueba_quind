package com.PaymentService.PaymentServiceAPI.infrastructure.persistence.adpter;

import com.PaymentService.PaymentServiceAPI.domain.model.PaymentAudit;
import com.PaymentService.PaymentServiceAPI.domain.repository.IPaymentAuditRepository;
import com.PaymentService.PaymentServiceAPI.infrastructure.persistence.mongodb.IReactiveMongoPaymentAuditRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PaymentAuditAdapter implements IPaymentAuditRepository {

    private final IReactiveMongoPaymentAuditRepository mongoRepository;

    public PaymentAuditAdapter(IReactiveMongoPaymentAuditRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }


    @Override
    public Mono<PaymentAudit> save(PaymentAudit audit) {
        return mongoRepository.save(audit);
    }

    @Override
    public Mono<PaymentAudit> findByOrderId(Long orderId) {
        return mongoRepository.findByOrderId(orderId);
    }
}
