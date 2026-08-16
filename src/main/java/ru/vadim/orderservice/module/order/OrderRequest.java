package ru.vadim.orderservice.module.order;

public record OrderRequest(
        String customerId, String productId, int quantity
) {
}
