package com.order_service.orderServiceAplication.application.dto;

import java.util.List;

public record OrderRequest(
        String customerId,
        List<ItemRequest> items
) {
}
