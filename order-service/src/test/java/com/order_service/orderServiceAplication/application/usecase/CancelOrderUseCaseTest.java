package com.order_service.orderServiceAplication.application.usecase;

import com.order_service.orderServiceAplication.domain.model.Order;
import com.order_service.orderServiceAplication.domain.model.OrderEvent;
import com.order_service.orderServiceAplication.domain.model.OrderStatus;
import com.order_service.orderServiceAplication.domain.repository.IOrderEventPublisher;
import com.order_service.orderServiceAplication.domain.repository.IOrderRepository;
import com.order_service.orderServiceAplication.domain.repository.IOrderEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CancelOrderUseCaseTest {
    @Mock private IOrderRepository repository;
    @Mock private IOrderEventPublisher eventPublisher;
    @Mock
    private IOrderEventRepository eventStore;
    @InjectMocks
    private CancelOrderUseCase useCase;

    @Test
    void shouldCancelOrderSuccessfully() {
        Order order = Order.builder().id(Long.valueOf("1")).status(OrderStatus.PENDING).build();

        when(repository.findById("1")).thenReturn(Mono.just(order));
        when(repository.save(any())).thenReturn(Mono.just(order));
        when(eventStore.save(any())).thenReturn(Mono.just(mock(OrderEvent.class)));
        when(eventPublisher.publishOrderCancelled(any())).thenReturn(Mono.empty());

        // EL SECRETO SENIOR: StepVerifier
        StepVerifier.create(useCase.execute("1"))
                .expectNextMatches(savedOrder -> savedOrder.getStatus() == OrderStatus.CANCELLED)
                .verifyComplete();
    }
}
