package com.order_service.orderServiceAplication.application.usecase;

<<<<<<< HEAD

=======
>>>>>>> develop_checkout_2
import com.order_service.orderServiceAplication.application.dto.OrderRequest;
import com.order_service.orderServiceAplication.domain.model.Order;
import com.order_service.orderServiceAplication.domain.model.OrderItem;
import com.order_service.orderServiceAplication.domain.model.OrderStatus;
import com.order_service.orderServiceAplication.domain.repository.IOrderEventPublisher;
import com.order_service.orderServiceAplication.domain.repository.IOrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

<<<<<<< HEAD
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
=======
import java.util.List;

@Service
public class CreateOrderUseCase {

    private final IOrderRepository orderRepository;
    private final IOrderEventPublisher orderEventPublisher;

    public CreateOrderUseCase(IOrderRepository orderRepository,
                              IOrderEventPublisher orderEventPublisher) {
        this.orderRepository = orderRepository;
        this.orderEventPublisher = orderEventPublisher;
    }

    public Mono<Order> execute(OrderRequest request) {
        // 1. Convertir DTO a Entidad de Dominio
        Order order = new Order();
        order.setCustomerId(request.customerId());
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> domainItems = request.items().stream()
                .map(i -> new OrderItem(i.productId(), i.quantity(), i.price()))
                .toList();

        order.setItems(domainItems);

        // 2. Ejecutar lógica de negocio del dominio
        order.calculateTotal();

        // 3. Persistir y retornar (Reactivo)
        return orderRepository.save(order).flatMap(savedOrder -> orderEventPublisher.publishOrderCreated(savedOrder)
                .thenReturn(savedOrder));
    }



}
>>>>>>> develop_checkout_2
