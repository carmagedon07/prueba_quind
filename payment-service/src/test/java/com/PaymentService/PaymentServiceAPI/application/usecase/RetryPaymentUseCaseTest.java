package com.PaymentService.PaymentServiceAPI.application.usecase;

import com.PaymentService.PaymentServiceAPI.domain.model.PaymentAudit;
import com.PaymentService.PaymentServiceAPI.domain.repository.IPaymentAuditRepository;
import com.PaymentService.PaymentServiceAPI.infrastructure.messaging.PaymentPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RetryPaymentUseCaseTest {

        @Mock
        private IPaymentAuditRepository repository;
        @Mock
        private PaymentPublisher publisher;

        @InjectMocks
        private RetryPaymentUseCase retryPaymentUseCase;

        @Test
        void shouldPublishMessageWhenPaymentExists() {
            // Arrange (Preparar)
            Long orderId = 8L;
            PaymentAudit mockAudit = PaymentAudit.builder()
                    .orderId(orderId)
                    .status("APPROVED")
                    .build();

            when(repository.findByOrderId(orderId)).thenReturn(Mono.just(mockAudit));

            // Act (Ejecutar)
            Mono<Void> result = retryPaymentUseCase.execute(orderId);

            // Assert (Verificar)
            StepVerifier.create(result)
                    .verifyComplete();

            verify(publisher, times(1)).publishPaymentResponse(eq(orderId), eq(true));
        }
}
