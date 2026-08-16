package ru.vadim.orderservice.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vadim.orderservice.module.order.OrderRequest;
import ru.vadim.orderservice.module.order.OrderResponse;
import ru.vadim.orderservice.service.OrderService;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("orders")
    public OrderResponse placeOrder(@Validated @RequestBody OrderRequest orderRequest) {
        return this.orderService.placeOrder(orderRequest);
    }
}
