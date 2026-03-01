package com.order_service.orderServiceAplication.application.dto;

<<<<<<< HEAD
public record OrderResponse(String id,
                            String customerId,
                            Double totalAmount,
                            String status) {

=======
public record OrderResponse(Long id,
                            String customerId,
                            Double totalAmount,
                            String status) {
>>>>>>> develop_checkout_2
}
