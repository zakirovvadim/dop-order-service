package ru.vadim.orderservice.client.impl;

import java.util.Map;
import java.util.function.Supplier;
import org.springframework.web.client.RestClient;
import ru.vadim.orderservice.client.ProductClient;
import ru.vadim.orderservice.exception.ApplicationExceptions;
import ru.vadim.orderservice.module.product.ProductStatus;

public class ProductServiceClient extends AbstractServiceClient implements ProductClient {

    private final RestClient restClient;

    public ProductServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public ProductStatus getProduct(String productId) {
        var errorMap = Map.<Integer, Supplier<ProductStatus>>of(404, () -> ApplicationExceptions.productNotFound(productId));
        return this.excecuteRequest(
                () -> this.restClient.get()
                        .uri("/{productId}", productId)
                        .retrieve()
                        .body(ProductStatus.class),
                errorMap
        );
    }

    @Override
    protected String getServiceName() {
        return "product-service";
    }
}
