package com.order_service.orderServiceAplication.infrastructure.persistence.adapter;

import com.order_service.orderServiceAplication.domain.model.Order;
<<<<<<< HEAD
import com.order_service.orderServiceAplication.domain.model.OrderStatus;
import com.order_service.orderServiceAplication.domain.repository.IOrderRepository;
import com.order_service.orderServiceAplication.infrastructure.persistence.entity.OrderEntity;
import com.order_service.orderServiceAplication.infrastructure.persistence.repository.IR2dbcOrderRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class OrderPersistenceAdapter implements IOrderRepository {

    private final IR2dbcOrderRepository orderRepository;

    public OrderPersistenceAdapter(IR2dbcOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
=======
import com.order_service.orderServiceAplication.domain.repository.IOrderRepository;
import com.order_service.orderServiceAplication.infrastructure.persistence.entity.OrderEntity;
import com.order_service.orderServiceAplication.infrastructure.persistence.repository.IR2dbcOrderRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class OrderPersistenceAdapter implements IOrderRepository {

    private final IR2dbcOrderRepository r2dbcRepository;

    public OrderPersistenceAdapter(IR2dbcOrderRepository r2dbcRepository) {
        this.r2dbcRepository = r2dbcRepository;
>>>>>>> develop_checkout_2
    }


    @Override
    public Mono<Order> save(Order order) {
<<<<<<< HEAD
        //1.Mappear del dominio a entidad base de datos
        OrderEntity entity = new OrderEntity();
        entity.setCustomerId(order.getCustomerId());
        entity.setStatus(order.getStatus() != null ? order.getStatus().name() : null);
        entity.setTotalAmount(order.getTotalAmount());
        //2.Persistir usando el repositorio reactivo
        return orderRepository.save(entity)
                .map(savedEntity -> {
                    //3.Mappear de vuelta a dominio
                    Order savedOrder = new Order();
                    savedOrder.setId(String.valueOf(savedEntity.getId()));
                    savedOrder.setCustomerId(savedEntity.getCustomerId());
                    if (savedEntity.getStatus() != null) {
                        savedOrder.setStatus(OrderStatus.valueOf(savedEntity.getStatus()));
                    }
                    savedOrder.setTotalAmount(savedEntity.getTotalAmount());
                    return savedOrder;
                });
    }

    @Override
    public Mono<Order> findById(Long id) {
        return orderRepository.findById(id)
                .map(entity -> {
                    Order order = new Order();
                    order.setId(String.valueOf(entity.getId()));
                    order.setCustomerId(entity.getCustomerId());
                    if (entity.getStatus() != null) {
                        order.setStatus(OrderStatus.valueOf(entity.getStatus()));
                    }
                    order.setTotalAmount(entity.getTotalAmount());
=======
        // 1. Mapear de Dominio a Entidad de DB
        OrderEntity entity = new OrderEntity();
        entity.setCustomerId(order.getCustomerId());
        entity.setTotalAmount(order.getTotalAmount());
        entity.setStatus(order.getStatus().name());

        // 2. Guardar y volver a mapear a Dominio
        return r2dbcRepository.save(entity)
                .map(savedEntity -> {
                    order.setId(Long.valueOf(savedEntity.getId().toString()));
>>>>>>> develop_checkout_2
                    return order;
                });
    }

    @Override
<<<<<<< HEAD
    public Flux<Order> findByCustomerId(String customerId, int page, int size) {
        // Implementación simple (sin paginación) - se puede adaptar a paginación si IR2dbcOrderRepository la soporta
        return orderRepository.findByCustomerId(customerId)
                .map(entity -> {
                    Order order = new Order();
                    order.setId(String.valueOf(entity.getId()));
                    order.setCustomerId(entity.getCustomerId());
                    if (entity.getStatus() != null) {
                        order.setStatus(OrderStatus.valueOf(entity.getStatus()));
                    }
                    order.setTotalAmount(entity.getTotalAmount());
                    return order;
=======
    public Mono<Order> findById(String id) {
        return r2dbcRepository.findById(Long.valueOf(id))
                .map(entity -> {
                    // Mapeo de vuelta a objeto de dominio...
                    return new Order();
>>>>>>> develop_checkout_2
                });
    }

    @Override
<<<<<<< HEAD
    public Flux<Order> findAll() {
        return orderRepository.findAll()
                .map(entity -> {
                    Order order = new Order();
                    order.setId(String.valueOf(entity.getId()));
                    order.setCustomerId(entity.getCustomerId());
                    if (entity.getStatus() != null) {
                        order.setStatus(OrderStatus.valueOf(entity.getStatus()));
                    }
                    order.setTotalAmount(entity.getTotalAmount());
                    return order;
                });
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return orderRepository.deleteById(id);
    }
=======
    public Flux<Order> findByCustomerId(String customerId, Pageable pageable) {
        return null;
    }


>>>>>>> develop_checkout_2
}
