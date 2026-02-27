package com.order_service.orderServiceAplication.infrastructure.persistence.adapter;

import com.order_service.orderServiceAplication.domain.model.Order;
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
    }


    @Override
    public Mono<Order> save(Order order) {
        // 1. Mapear de Dominio a Entidad de DB
        OrderEntity entity = new OrderEntity();
        entity.setCustomerId(order.getCustomerId());
        entity.setTotalAmount(order.getTotalAmount());
        entity.setStatus(order.getStatus().name());

        // 2. Guardar y volver a mapear a Dominio
        return r2dbcRepository.save(entity)
                .map(savedEntity -> {
                    order.setId(Long.valueOf(savedEntity.getId().toString()));
                    return order;
                });
    }

    @Override
    public Mono<Order> findById(String id) {
        return r2dbcRepository.findById(Long.valueOf(id))
                .map(entity -> {
                    // Mapeo de vuelta a objeto de dominio...
                    return new Order();
                });
    }

    @Override
    public Flux<Order> findByCustomerId(String customerId, Pageable pageable) {
        return null;
    }


}
