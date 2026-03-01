package com.PaymentService.PaymentServiceAPI.infrastructure.rest;

import com.PaymentService.PaymentServiceAPI.application.usecase.GetPaymentStatusUseCase;
import com.PaymentService.PaymentServiceAPI.application.usecase.RetryPaymentUseCase;
import com.PaymentService.PaymentServiceAPI.domain.model.PaymentAudit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.bean.override.mockito.MockitoBean; // ✅ Spring Boot 4
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;

import static org.mockito.Mockito.when;

@WebFluxTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private GetPaymentStatusUseCase getStatusUseCase;

    @MockitoBean
    private RetryPaymentUseCase retryUseCase;

    @Test
    void shouldReturnPaymentStatus() {
        Long orderId = 10L;
        PaymentAudit mockAudit = PaymentAudit.builder()
                .orderId(orderId)
                .status("APPROVED")
                .build();

        when(getStatusUseCase.execute(orderId)).thenReturn(Mono.just(mockAudit));

        webTestClient.get()
                .uri("/api/v1/payments/{orderId}", orderId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("APPROVED")
                .jsonPath("$.orderId").isEqualTo(10);
    }
}