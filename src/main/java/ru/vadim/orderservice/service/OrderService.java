package ru.vadim.orderservice.service;

import ru.vadim.orderservice.module.order.OrderRequest;
import ru.vadim.orderservice.module.order.OrderResponse;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest request);
}
