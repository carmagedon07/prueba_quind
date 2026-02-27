package com.order_service.orderServiceAplication.application.usecase;


import com.order_service.orderServiceAplication.application.dto.OrderRequest;
import com.order_service.orderServiceAplication.domain.model.Order;
import com.order_service.orderServiceAplication.domain.model.OrderItem;
import com.order_service.orderServiceAplication.domain.model.OrderStatus;
import com.order_service.orderServiceAplication.domain.repository.IOrderEventPublisher;
import com.order_service.orderServiceAplication.domain.repository.IOrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
 public class CreateOrderUseCase {

     private final IOrderRepository orderRepository;
     private final IOrderEventPublisher eventPublisher;


     public CreateOrderUseCase(IOrderRepository orderRepository
             ,IOrderEventPublisher eventPublisher) {
         this.orderRepository = orderRepository;
         this.eventPublisher = eventPublisher;
     }

     public Mono<Order> execute(OrderRequest orderRequest) {
         Objects.requireNonNull(orderRequest, "orderRequest must not be null");
         //1. convierte DTO a una entidad del dominio
         Order order = new Order();
         order.setCustomerId(orderRequest.customerId());
         order.setStatus(OrderStatus.PENDING);

         List<OrderItem> domainItems = (orderRequest.items() != null
                 ? orderRequest.items().stream()
                 .map(item -> new OrderItem(item.productId(), item.quantity(), item.price()))
                 .toList()
                 : Collections.emptyList());

         // asigna los items y calcula total antes de persistir
         order.setItems(domainItems);
         order.calculateTotal();

         // persiste y devuelve el resultado como Mono<Order>
         return orderRepository
         .save(order)
         .flatMap(savedOrder -> eventPublisher.publishOrderCreatedEvent(savedOrder)
                 .thenReturn(savedOrder));
     }

 }
