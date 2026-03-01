package com.order_service.orderServiceAplication.application.dto;

import java.util.List;

<<<<<<< HEAD
public record OrderRequest(String customerId,
                           List<ItemRequest> items) {
=======
public record OrderRequest(
        String customerId,
        List<ItemRequest> items
) {
>>>>>>> develop_checkout_2
}
