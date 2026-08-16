package ru.vadim.orderservice.service.impl;

import java.time.LocalDateTime;
import ru.vadim.orderservice.client.CustomerClient;
import ru.vadim.orderservice.client.ProductClient;
import ru.vadim.orderservice.exception.ApplicationExceptions;
import ru.vadim.orderservice.module.order.CreateOrderCommand;
import ru.vadim.orderservice.module.order.Order;
import ru.vadim.orderservice.module.order.OrderItem;
import ru.vadim.orderservice.module.product.Product;
import ru.vadim.orderservice.module.product.ProductStatus.Active;
import ru.vadim.orderservice.module.product.ProductStatus.Discountinued;
import ru.vadim.orderservice.service.RequestValidatorService;

public class RequestValidatorServiceImpl implements RequestValidatorService {

    private final ProductClient productClient;
    private final CustomerClient customerClient;

    public RequestValidatorServiceImpl(ProductClient productClient, CustomerClient customerClient) {
        this.productClient = productClient;
        this.customerClient = customerClient;
    }

    @Override
    public Order validate(CreateOrderCommand request) {
        var product = this.getProduct(request.productId());
        var customer = this.customerClient.getCustomer(request.customerId());
        var orderItem = new OrderItem(product, request.quantity());
        return new Order(request.orderId(), customer, orderItem, LocalDateTime.now());
    }

    private Product getProduct(String productId) {
        return switch (this.productClient.getProduct(productId)) {
            case Active active -> active.product();
            case Discountinued discountinued -> ApplicationExceptions.discontinuedNotFound(discountinued);
        };
    }
}
