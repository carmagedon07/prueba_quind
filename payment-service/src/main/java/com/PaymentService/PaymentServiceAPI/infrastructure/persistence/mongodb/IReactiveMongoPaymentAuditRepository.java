package com.PaymentService.PaymentServiceAPI.infrastructure.persistence.mongodb;


import com.PaymentService.PaymentServiceAPI.domain.model.PaymentAudit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IReactiveMongoPaymentAuditRepository extends ReactiveMongoRepository<PaymentAudit, String> {
    Mono<PaymentAudit> findByOrderId(Long orderId);
}
