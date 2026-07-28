package ru.vadim.orderservice.module.order;

import ru.vadim.orderservice.module.product.Product;

public record OrderItem(Product product,
                        int quantity) {
}
