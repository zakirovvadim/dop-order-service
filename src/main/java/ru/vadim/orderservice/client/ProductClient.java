package ru.vadim.orderservice.client;

import ru.vadim.orderservice.module.product.ProductStatus;

public interface ProductClient {

    ProductStatus getProduct(String productId);
}
