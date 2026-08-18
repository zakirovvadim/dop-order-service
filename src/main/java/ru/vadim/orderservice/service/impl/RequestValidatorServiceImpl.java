package ru.vadim.orderservice.service.impl;

import java.time.LocalDateTime;
import ru.vadim.orderservice.client.CouponClient;
import ru.vadim.orderservice.client.CustomerClient;
import ru.vadim.orderservice.client.ProductClient;
import ru.vadim.orderservice.exception.ApplicationExceptions;
import ru.vadim.orderservice.module.coupon.Coupon;
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
    private final CouponClient couponClient;

    public RequestValidatorServiceImpl(ProductClient productClient, CustomerClient customerClient, CouponClient couponClient) {
        this.productClient = productClient;
        this.customerClient = customerClient;
        this.couponClient = couponClient;
    }

    @Override
    public Order validate(CreateOrderCommand request) {
        var product = this.getProduct(request.productId());
        var customer = this.customerClient.getCustomer(request.customerId());
        var orderItem = new OrderItem(product, request.quantity());
        var coupon = request.couponCode()
                .map(this.couponClient::getCoupon)
                .orElse(Coupon.none());
        return new Order(request.orderId(), customer, orderItem, coupon, LocalDateTime.now());
    }

    private Product getProduct(String productId) {
        return switch (this.productClient.getProduct(productId)) {
            case Active active -> active.product();
            case Discountinued discountinued -> ApplicationExceptions.discontinuedNotFound(discountinued);
        };
    }
}
